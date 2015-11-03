package com.floreantpos.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.model.base.BaseTicket;
import com.floreantpos.util.NumberUtil;
import com.floreantpos.util.POSUtil;

@XmlRootElement(name = "ticket")
public class Ticket extends BaseTicket {
  private static final long serialVersionUID = 1L;
  // public final static int TAKE_OUT = -1;

  // public final static String DINE_IN = "DINE IN";
  // public final static String TAKE_OUT = "TAKE OUT";
  // public final static String PICKUP = "PICKUP";
  // public final static String HOME_DELIVERY = "HOME DELIVERY";
  // public final static String DRIVE_THROUGH = "DRIVE THRU";
  // public final static String BAR_TAB = "BAR_TAB";

  public final static String PROPERTY_CARD_TRANSACTION_ID = "card_transaction_id";
  public final static String PROPERTY_CARD_TRACKS = "card_tracks";
  public static final String PROPERTY_CARD_NAME = "card_name";
  public static final String PROPERTY_PAYMENT_METHOD = "payment_method";
  public static final String PROPERTY_CARD_READER = "card_reader";
  public static final String PROPERTY_CARD_NUMBER = "card_number";
  public static final String PROPERTY_CARD_EXP_YEAR = "card_exp_year";
  public static final String PROPERTY_CARD_EXP_MONTH = "card_exp_month";
  public static final String PROPERTY_ADVANCE_PAYMENT = "advance_payment";
  public static final String PROPERTY_CARD_AUTH_CODE = "card_auth_code";

  /* [CONSTRUCTOR MARKER BEGIN] */
  public Ticket() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public Ticket(java.lang.Integer id) {
    super(id);
  }

  /* [CONSTRUCTOR MARKER END] */

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy, h:m a");
  private DecimalFormat numberFormat = new DecimalFormat("0.00");

  private List deletedItems;

  // additional properties
  public static final String PAYMENT_TYPE = "PAYMENT_TYPE";

  @Override
  public void setClosed(Boolean closed) {
    super.setClosed(closed);
  }

  @Override
  public void setCreateDate(Date createDate) {
    super.setCreateDate(createDate);
    super.setActiveDate(createDate);
  }

  @Override
  public Date getDeliveryDate() {
    Date deliveryDate = super.getDeliveryDate();

    if (deliveryDate == null) {
      deliveryDate = getCreateDate();
      Calendar c = Calendar.getInstance();
      c.setTime(deliveryDate);
      c.add(Calendar.MINUTE, 10);
      deliveryDate = c.getTime();
    }

    return deliveryDate;
  }

  @Override
  public List<TicketItem> getTicketItems() {
    List<TicketItem> items = super.getTicketItems();

    if (items == null) {
      items = new ArrayList<TicketItem>();
      super.setTicketItems(items);
    }
    return items;
  }

  public Ticket(User owner, Date createTime) {
    setOwner(owner);
    setCreateDate(createTime);
  }

  public String getCreateDateFormatted() {
    return dateFormat.format(getCreateDate());
  }

  public String getTitle() {
    String title = "";
    if (getId() != null) {
      title += "#" + getId();
    }
    title += " Server" + ": " + getOwner();
    title += " Create on" + ":" + getCreateDateFormatted();
    title += " Total" + ": " + numberFormat.format(getTotalAmount());

    return title;
  }

  public int getBeverageCount() {
    List<TicketItem> ticketItems = getTicketItems();
    if (ticketItems == null)
      return 0;

    int count = 0;
    for (TicketItem ticketItem : ticketItems) {
      if (ticketItem.isBeverage()) {
        count += ticketItem.getItemCount();
      }
    }
    return count;
  }

  public void calculatePrice() {
    List<TicketItem> ticketItems = getTicketItems();
    if (ticketItems == null) {
      return;
    }

    // check whether has member information
    boolean hasMemberInfo = false;
    if (getCustomer() != null) {
      hasMemberInfo = true;
    }

    for (TicketItem ticketItem : ticketItems) {
      ticketItem.calculatePrice(hasMemberInfo);
    }

    double subtotalAmount = calculateSubtotalAmount();
    double discountAmount = calculateDiscountAmount();

    setSubtotalAmount(subtotalAmount);
    setDiscountAmount(discountAmount);

    double totalAmount = 0.0;

    totalAmount = subtotalAmount - discountAmount;
    totalAmount = fixInvalidAmount(totalAmount);

    setTotalAmount(NumberUtil.roundToTwoDigit(totalAmount));

    double dueAmount = totalAmount - getPaidAmount();
    setDueAmount(NumberUtil.roundToTwoDigit(dueAmount));
  }

  private double calculateSubtotalAmount() {
    double subtotalAmount = 0;

    List<TicketItem> ticketItems = getTicketItems();
    if (ticketItems == null) {
      return subtotalAmount;
    }

    for (TicketItem ticketItem : ticketItems) {
      subtotalAmount += ticketItem.getSubtotalAmount();
    }

    subtotalAmount = fixInvalidAmount(subtotalAmount);

    return NumberUtil.roundToTwoDigit(subtotalAmount);
  }

  private double calculateDiscountAmount() {
    double subtotalAmount = getSubtotalAmount();
    double discountAmount = 0;

    List<TicketItem> ticketItems = getTicketItems();
    if (ticketItems != null) {
      for (TicketItem ticketItem : ticketItems) {
        discountAmount += ticketItem.getDiscountAmount();
      }
    }

    List<TicketCoupon> discounts = getCoupons();
    if (discounts != null) {
      for (TicketCoupon discount : discounts) {
        discountAmount += calculateDiscountFromType(discount, subtotalAmount);
      }
    }

    discountAmount = fixInvalidAmount(discountAmount);

    return NumberUtil.roundToTwoDigit(discountAmount);
  }

  private double fixInvalidAmount(double tax) {
    if (tax < 0 || Double.isNaN(tax)) {
      tax = 0;
    }
    return tax;
  }

  public double calculateDiscountFromType(TicketCoupon coupon, double subtotal) {
    List<TicketItem> ticketItems = getTicketItems();

    double discount = 0;
    int type = coupon.getType();
    double couponValue = coupon.getValue();

    switch (type) {
      case Coupon.FIXED_PER_ORDER:
        discount += couponValue;
        break;

      case Coupon.FIXED_PER_CATEGORY:
        HashSet<Integer> categoryIds = new HashSet<Integer>();
        for (TicketItem item : ticketItems) {
          Integer itemId = item.getItemId();
          if (!categoryIds.contains(itemId)) {
            discount += couponValue;
            categoryIds.add(itemId);
          }
        }
        break;

      case Coupon.FIXED_PER_ITEM:
        for (TicketItem item : ticketItems) {
          discount += (couponValue * item.getItemCount());
        }
        break;

      case Coupon.PERCENTAGE_PER_ORDER:
        discount += ((subtotal * couponValue) / 100.0);
        break;

      case Coupon.PERCENTAGE_PER_CATEGORY:
        categoryIds = new HashSet<Integer>();
        for (TicketItem item : ticketItems) {
          Integer itemId = item.getItemId();
          if (!categoryIds.contains(itemId)) {
            discount += ((item.getUnitPrice() * couponValue) / 100.0);
            categoryIds.add(itemId);
          }
        }
        break;

      case Coupon.PERCENTAGE_PER_ITEM:
        for (TicketItem item : ticketItems) {
          discount += ((item.getSubtotalAmount() * couponValue) / 100.0);
        }
        break;

      case Coupon.FREE_AMOUNT:
        discount += couponValue;
        break;
    }
    return discount;
  }

  public void addDeletedItems(Object o) {
    if (deletedItems == null) {
      deletedItems = new ArrayList();
    }

    deletedItems.add(o);
  }

  public List getDeletedItems() {
    return deletedItems;
  }

  public void clearDeletedItems() {
    if (deletedItems != null) {
      deletedItems.clear();
    }

    deletedItems = null;
  }

  public boolean needsKitchenPrint() {
    if (getDeletedItems() != null && getDeletedItems().size() > 0) {
      return true;
    }

    List<TicketItem> ticketItems = getTicketItems();
    for (TicketItem item : ticketItems) {
      if (item.isShouldPrintToKitchen() && !item.isPrintedToKitchen()) {
        return true;
      }

      List<TicketItemCookingInstruction> cookingInstructions = item.getCookingInstructions();
      if (cookingInstructions != null) {
        for (TicketItemCookingInstruction ticketItemCookingInstruction : cookingInstructions) {
          if (!ticketItemCookingInstruction.isPrintedToKitchen()) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public TicketType getType() {
    String type = getTicketType();

    if (StringUtils.isEmpty(type)) {
      return TicketType.DINE_IN;
    }

    return TicketType.valueOf(type);
  }

  public void setType(TicketType type) {
    setTicketType(type.name());
  }

  public void addProperty(String name, String value) {
    if (getProperties() == null) {
      setProperties(new HashMap<String, String>());
    }

    getProperties().put(name, value);
  }

  public boolean hasProperty(String key) {
    return getProperty(key) != null;
  }

  public String getProperty(String key) {
    if (getProperties() == null) {
      return null;
    }

    return getProperties().get(key);
  }

  public String getProperty(String key, String defaultValue) {
    if (getProperties() == null) {
      return null;
    }

    String string = getProperties().get(key);
    if (StringUtils.isEmpty(string)) {
      return defaultValue;
    }

    return string;
  }

  public void removeProperty(String propertyName) {
    Map<String, String> properties = getProperties();
    if (properties == null) {
      return;
    }

    properties.remove(propertyName);
  }

  public boolean isPropertyValueTrue(String propertyName) {
    String property = getProperty(propertyName);

    return POSUtil.getBoolean(property);
  }

  public String toURLForm() {
    String s = "ticket_id=" + getId();

    List<TicketItem> items = getTicketItems();
    if (items == null || items.size() == 0) {
      return s;
    }

    for (int i = 0; i < items.size(); i++) {
      TicketItem ticketItem = items.get(i);
      s += "&items[" + i + "][id]=" + ticketItem.getId();
      s += "&items[" + i + "][name]=" + POSUtil.encodeURLString(ticketItem.getName());
      s += "&items[" + i + "][price]=" + ticketItem.getSubtotalAmount();
    }

    s += "&subtotal=" + getSubtotalAmount();
    s += "&grandtotal=" + getTotalAmount();

    return s;
  }

  public boolean isPaidBy(PaymentType paymentType) {
    String property = getProperty(PAYMENT_TYPE);
    if (StringUtils.equals(paymentType.name(), property)) {
      return true;
    }

    return false;
  }

  public void removeCustomer() {
    this.customerPhone = null;
    setCustomer(null);
  }

  public String getTicketStatus() {
    if (voided) {
      return String.format(POSConstants.TICKET_EXPLORER_TABLE_STATUS_VOIDED, voidReason);
    }

    if (paid && closed) {
      return POSConstants.TICKET_EXPLORER_TABLE_STATUS_FINISHED;
    }

    if (!paid) {
      return POSConstants.TICKET_EXPLORER_TABLE_STATUS_UNPAID;
    }

    if (refunded) {
      return POSConstants.TICKET_EXPLORER_TABLE_STATUS_REFUNDED;
    }

    return POSConstants.TICKET_EXPLORER_TABLE_STATUS_UNKNOWN;
  }

  //  @Override
  //  public String getPaymentType() {
  //    StringBuilder paymentTypes = new StringBuilder("");
  //    for (PosTransaction trans : getTransactions()) {
  //      paymentTypes
  //          .append(PaymentType.getPaymentTypeFromType(trans.getPaymentType()).getDisplayString());
  //      paymentTypes.append(" ");
  //    }
  //
  //    return paymentTypes.toString();
  //  }
}
