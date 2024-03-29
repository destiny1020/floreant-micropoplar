package com.floreantpos.model.base;

import java.io.Serializable;

import com.floreantpos.model.Customer;


/**
 * This is an object that contains data related to the TICKET table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 *
 * @hibernate.class table="TICKET"
 */

public abstract class BaseTicket implements Comparable<BaseTicket>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static String REF = "Ticket";
  public static String PROP_RE_OPENED = "reOpened";
  public static String PROP_VOID_REASON = "voidReason";
  public static String PROP_DUE_AMOUNT = "dueAmount";
  public static String PROP_DISCOUNT_AMOUNT = "discountAmount";
  public static String PROP_CREATE_DATE = "createDate";
  public static String PROP_DELIVERY_CHARGE = "deliveryCharge";
  public static String PROP_PAID = "paid";
  public static String PROP_ACTIVE_DATE = "activeDate";
  public static String PROP_CREATION_HOUR = "creationHour";
  public static String PROP_CUSTOMER_WILL_PICKUP = "customerWillPickup";
  public static String PROP_OWNER = "owner";
  public static String PROP_DELIVERY_DATE = "deliveryDate";
  public static String PROP_TERMINAL = "terminal";
  public static String PROP_CLOSED = "closed";
  public static String PROP_CLOSING_DATE = "closingDate";
  public static String PROP_DELIVERY_ADDRESS = "deliveryAddress";
  public static String PROP_REFUNDED = "refunded";
  public static String PROP_STATUS = "status";
  public static String PROP_SUBTOTAL_AMOUNT = "subtotalAmount";
  public static String PROP_VOIDED_BY = "voidedBy";
  public static String PROP_TICKET_TYPE = "ticketType";
  public static String PROP_ID = "id";
  public static String PROP_WASTED = "wasted";
  public static String PROP_VOIDED = "voided";
  public static String PROP_TOTAL_AMOUNT = "totalAmount";
  public static String PROP_PAID_AMOUNT = "paidAmount";
  public static String PROP_EXTRA_DELIVERY_INFO = "extraDeliveryInfo";
  public static String PROP_UNIQ_ID = "uniqId";
  public static String PROP_DINE_IN_NUMBER = "dineInNumber";
  public static String PROP_CUSTOMER = "customer";
  public static String PROP_CUSTOMER_PHONE = "customerPhone";
  public static String PROP_PAYMENT_TYPE = "paymentType";
  public static String PROP_RECEIVED_AMOUNT = "receivedAmount";
  public static String PROP_CHANGE_AMOUNT = "changeAmount";

  // constructors
  public BaseTicket() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseTicket(java.lang.Integer id) {
    this.setId(id);
    initialize();
  }

  protected void initialize() {}



  private int hashCode = Integer.MIN_VALUE;

  // primary key
  private java.lang.Integer id;

  // unique ID
  private String uniqId;

  java.util.Date modifiedTime;

  // fields
  protected java.util.Date createDate;
  protected java.util.Date closingDate;
  protected java.util.Date activeDate;
  protected java.util.Date deliveryDate;
  protected java.lang.Integer creationHour;
  protected java.lang.Boolean paid;
  protected java.lang.Boolean voided;
  protected java.lang.String voidReason;
  protected java.lang.Boolean wasted;
  protected java.lang.Boolean refunded;
  protected java.lang.Boolean closed;
  protected java.lang.Double subtotalAmount;
  protected java.lang.Double discountAmount;
  protected java.lang.Double totalAmount;
  protected java.lang.Double paidAmount;
  protected java.lang.Double dueAmount;
  protected java.lang.String status;
  protected java.lang.Boolean reOpened;
  protected java.lang.Double deliveryCharge;
  protected java.lang.String deliveryAddress;
  protected java.lang.Boolean customerWillPickup;
  protected java.lang.String extraDeliveryInfo;
  protected java.lang.String ticketType;
  protected java.lang.Integer dineInNumber;
  protected String customerPhone;
  protected String paymentType;
  protected Double receivedAmount;
  protected Double changeAmount;

  // many to one
  private com.floreantpos.model.User owner;
  private com.floreantpos.model.User voidedBy;
  private com.floreantpos.model.Terminal terminal;
  private Customer customer;

  // collections
  private java.util.Map<String, String> properties;
  private java.util.List<com.floreantpos.model.TicketItem> ticketItems;
  private java.util.List<com.floreantpos.model.TicketCoupon> coupons;
  private java.util.Set<com.floreantpos.model.PosTransaction> transactions;

  /**
   * Return the unique identifier of this class
   * 
   * @hibernate.id generator-class="identity" column="ID"
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Set the unique identifier of this class
   * 
   * @param id the new ID
   */
  public void setId(java.lang.Integer id) {
    this.id = id;
    this.hashCode = Integer.MIN_VALUE;
  }



  /**
   * Return the value associated with the column: MODIFIED_TIME
   */
  public java.util.Date getModifiedTime() {
    return modifiedTime;
  }

  /**
   * Set the value related to the column: MODIFIED_TIME
   * 
   * @param modifiedTime the MODIFIED_TIME value
   */
  public void setModifiedTime(java.util.Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }



  /**
   * Return the value associated with the column: CREATE_DATE
   */
  public java.util.Date getCreateDate() {
    return createDate;
  }

  /**
   * Set the value related to the column: CREATE_DATE
   * 
   * @param createDate the CREATE_DATE value
   */
  public void setCreateDate(java.util.Date createDate) {
    this.createDate = createDate;
  }



  /**
   * Return the value associated with the column: CLOSING_DATE
   */
  public java.util.Date getClosingDate() {
    return closingDate;
  }

  /**
   * Set the value related to the column: CLOSING_DATE
   * 
   * @param closingDate the CLOSING_DATE value
   */
  public void setClosingDate(java.util.Date closingDate) {
    this.closingDate = closingDate;
  }



  /**
   * Return the value associated with the column: ACTIVE_DATE
   */
  public java.util.Date getActiveDate() {
    return activeDate;
  }

  /**
   * Set the value related to the column: ACTIVE_DATE
   * 
   * @param activeDate the ACTIVE_DATE value
   */
  public void setActiveDate(java.util.Date activeDate) {
    this.activeDate = activeDate;
  }



  /**
   * Return the value associated with the column: DELIVEERY_DATE
   */
  public java.util.Date getDeliveryDate() {
    return deliveryDate;
  }

  /**
   * Set the value related to the column: DELIVEERY_DATE
   * 
   * @param deliveryDate the DELIVEERY_DATE value
   */
  public void setDeliveryDate(java.util.Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }



  /**
   * Return the value associated with the column: CREATION_HOUR
   */
  public java.lang.Integer getCreationHour() {
    return creationHour == null ? Integer.valueOf(0) : creationHour;
  }

  /**
   * Set the value related to the column: CREATION_HOUR
   * 
   * @param creationHour the CREATION_HOUR value
   */
  public void setCreationHour(java.lang.Integer creationHour) {
    this.creationHour = creationHour;
  }



  /**
   * Return the value associated with the column: PAID
   */
  public java.lang.Boolean isPaid() {
    return paid == null ? Boolean.FALSE : paid;
  }

  /**
   * Set the value related to the column: PAID
   * 
   * @param paid the PAID value
   */
  public void setPaid(java.lang.Boolean paid) {
    this.paid = paid;
  }



  /**
   * Return the value associated with the column: VOIDED
   */
  public java.lang.Boolean isVoided() {
    return voided == null ? Boolean.FALSE : voided;
  }

  /**
   * Set the value related to the column: VOIDED
   * 
   * @param voided the VOIDED value
   */
  public void setVoided(java.lang.Boolean voided) {
    this.voided = voided;
  }



  /**
   * Return the value associated with the column: VOID_REASON
   */
  public java.lang.String getVoidReason() {
    return voidReason;
  }

  /**
   * Set the value related to the column: VOID_REASON
   * 
   * @param voidReason the VOID_REASON value
   */
  public void setVoidReason(java.lang.String voidReason) {
    this.voidReason = voidReason;
  }



  /**
   * Return the value associated with the column: WASTED
   */
  public java.lang.Boolean isWasted() {
    return wasted == null ? Boolean.FALSE : wasted;
  }

  /**
   * Set the value related to the column: WASTED
   * 
   * @param wasted the WASTED value
   */
  public void setWasted(java.lang.Boolean wasted) {
    this.wasted = wasted;
  }



  /**
   * Return the value associated with the column: REFUNDED
   */
  public java.lang.Boolean isRefunded() {
    return refunded == null ? Boolean.FALSE : refunded;
  }

  /**
   * Set the value related to the column: REFUNDED
   * 
   * @param refunded the REFUNDED value
   */
  public void setRefunded(java.lang.Boolean refunded) {
    this.refunded = refunded;
  }



  /**
   * Return the value associated with the column: SETTLED
   */
  public java.lang.Boolean isClosed() {
    return closed == null ? Boolean.FALSE : closed;
  }

  /**
   * Set the value related to the column: SETTLED
   * 
   * @param closed the SETTLED value
   */
  public void setClosed(java.lang.Boolean closed) {
    this.closed = closed;
  }

  /**
   * Return the value associated with the column: SUB_TOTAL
   */
  public java.lang.Double getSubtotalAmount() {
    return subtotalAmount == null ? Double.valueOf(0) : subtotalAmount;
  }

  /**
   * Set the value related to the column: SUB_TOTAL
   * 
   * @param subtotalAmount the SUB_TOTAL value
   */
  public void setSubtotalAmount(java.lang.Double subtotalAmount) {
    this.subtotalAmount = subtotalAmount;
  }



  /**
   * Return the value associated with the column: TOTAL_DISCOUNT
   */
  public java.lang.Double getDiscountAmount() {
    return discountAmount == null ? Double.valueOf(0) : discountAmount;
  }

  /**
   * Set the value related to the column: TOTAL_DISCOUNT
   * 
   * @param discountAmount the TOTAL_DISCOUNT value
   */
  public void setDiscountAmount(java.lang.Double discountAmount) {
    this.discountAmount = discountAmount;
  }

  /**
   * Return the value associated with the column: TOTAL_PRICE
   */
  public java.lang.Double getTotalAmount() {
    return totalAmount == null ? Double.valueOf(0) : totalAmount;
  }

  /**
   * Set the value related to the column: TOTAL_PRICE
   * 
   * @param totalAmount the TOTAL_PRICE value
   */
  public void setTotalAmount(java.lang.Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  /**
   * Return the value associated with the column: PAID_AMOUNT
   */
  public java.lang.Double getPaidAmount() {
    return paidAmount == null ? Double.valueOf(0) : paidAmount;
  }

  /**
   * Set the value related to the column: PAID_AMOUNT
   * 
   * @param paidAmount the PAID_AMOUNT value
   */
  public void setPaidAmount(java.lang.Double paidAmount) {
    this.paidAmount = paidAmount;
  }



  /**
   * Return the value associated with the column: DUE_AMOUNT
   */
  public java.lang.Double getDueAmount() {
    return dueAmount == null ? Double.valueOf(0) : dueAmount;
  }

  /**
   * Set the value related to the column: DUE_AMOUNT
   * 
   * @param dueAmount the DUE_AMOUNT value
   */
  public void setDueAmount(java.lang.Double dueAmount) {
    this.dueAmount = dueAmount;
  }

  /**
   * Return the value associated with the column: STATUS
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * Set the value related to the column: STATUS
   * 
   * @param status the STATUS value
   */
  public void setStatus(java.lang.String status) {
    this.status = status;
  }

  /**
   * Return the value associated with the column: IS_RE_OPENED
   */
  public java.lang.Boolean isReOpened() {
    return reOpened == null ? Boolean.FALSE : reOpened;
  }

  /**
   * Set the value related to the column: IS_RE_OPENED
   * 
   * @param reOpened the IS_RE_OPENED value
   */
  public void setReOpened(java.lang.Boolean reOpened) {
    this.reOpened = reOpened;
  }

  /**
   * Return the value associated with the column: DELIVERY_CHARGE
   */
  public java.lang.Double getDeliveryCharge() {
    return deliveryCharge == null ? Double.valueOf(0) : deliveryCharge;
  }

  /**
   * Set the value related to the column: DELIVERY_CHARGE
   * 
   * @param deliveryCharge the DELIVERY_CHARGE value
   */
  public void setDeliveryCharge(java.lang.Double deliveryCharge) {
    this.deliveryCharge = deliveryCharge;
  }



  /**
   * Return the value associated with the column: DELIVERY_ADDRESS
   */
  public java.lang.String getDeliveryAddress() {
    return deliveryAddress;
  }

  /**
   * Set the value related to the column: DELIVERY_ADDRESS
   * 
   * @param deliveryAddress the DELIVERY_ADDRESS value
   */
  public void setDeliveryAddress(java.lang.String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }



  /**
   * Return the value associated with the column: CUSTOMER_PICKEUP
   */
  public java.lang.Boolean isCustomerWillPickup() {
    return customerWillPickup == null ? Boolean.FALSE : customerWillPickup;
  }

  /**
   * Set the value related to the column: CUSTOMER_PICKEUP
   * 
   * @param customerWillPickup the CUSTOMER_PICKEUP value
   */
  public void setCustomerWillPickup(java.lang.Boolean customerWillPickup) {
    this.customerWillPickup = customerWillPickup;
  }



  /**
   * Return the value associated with the column: DELIVERY_EXTRA_INFO
   */
  public java.lang.String getExtraDeliveryInfo() {
    return extraDeliveryInfo;
  }

  /**
   * Set the value related to the column: DELIVERY_EXTRA_INFO
   * 
   * @param extraDeliveryInfo the DELIVERY_EXTRA_INFO value
   */
  public void setExtraDeliveryInfo(java.lang.String extraDeliveryInfo) {
    this.extraDeliveryInfo = extraDeliveryInfo;
  }



  /**
   * Return the value associated with the column: TICKET_TYPE
   */
  public java.lang.String getTicketType() {
    return ticketType;
  }

  /**
   * Set the value related to the column: TICKET_TYPE
   * 
   * @param ticketType the TICKET_TYPE value
   */
  public void setTicketType(java.lang.String ticketType) {
    this.ticketType = ticketType;
  }

  /**
   * Return the value associated with the column: OWNER_ID
   */
  public com.floreantpos.model.User getOwner() {
    return owner;
  }

  /**
   * Set the value related to the column: OWNER_ID
   * 
   * @param owner the OWNER_ID value
   */
  public void setOwner(com.floreantpos.model.User owner) {
    this.owner = owner;
  }

  /**
   * Return the value associated with the column: VOID_BY_USER
   */
  public com.floreantpos.model.User getVoidedBy() {
    return voidedBy;
  }

  /**
   * Set the value related to the column: VOID_BY_USER
   * 
   * @param voidedBy the VOID_BY_USER value
   */
  public void setVoidedBy(com.floreantpos.model.User voidedBy) {
    this.voidedBy = voidedBy;
  }



  /**
   * Return the value associated with the column: TERMINAL_ID
   */
  public com.floreantpos.model.Terminal getTerminal() {
    return terminal;
  }

  /**
   * Set the value related to the column: TERMINAL_ID
   * 
   * @param terminal the TERMINAL_ID value
   */
  public void setTerminal(com.floreantpos.model.Terminal terminal) {
    this.terminal = terminal;
  }



  /**
   * Return the value associated with the column: properties
   */
  public java.util.Map<String, String> getProperties() {
    return properties;
  }

  /**
   * Set the value related to the column: properties
   * 
   * @param properties the properties value
   */
  public void setProperties(java.util.Map<String, String> properties) {
    this.properties = properties;
  }



  /**
   * Return the value associated with the column: ticketItems
   */
  public java.util.List<com.floreantpos.model.TicketItem> getTicketItems() {
    return ticketItems;
  }

  /**
   * Set the value related to the column: ticketItems
   * 
   * @param ticketItems the ticketItems value
   */
  public void setTicketItems(java.util.List<com.floreantpos.model.TicketItem> ticketItems) {
    this.ticketItems = ticketItems;
  }

  public void addToticketItems(com.floreantpos.model.TicketItem ticketItem) {
    if (null == getTicketItems())
      setTicketItems(new java.util.ArrayList<com.floreantpos.model.TicketItem>());
    getTicketItems().add(ticketItem);
  }



  /**
   * Return the value associated with the column: coupons
   */
  public java.util.List<com.floreantpos.model.TicketCoupon> getCoupons() {
    return coupons;
  }

  /**
   * Set the value related to the column: coupons
   * 
   * @param coupons the coupons value
   */
  public void setCoupons(java.util.List<com.floreantpos.model.TicketCoupon> coupons) {
    this.coupons = coupons;
  }

  public void addToCoupons(com.floreantpos.model.TicketCoupon ticketCoupon) {
    if (null == getCoupons())
      setCoupons(new java.util.ArrayList<com.floreantpos.model.TicketCoupon>());
    getCoupons().add(ticketCoupon);
  }



  /**
   * Return the value associated with the column: transactions
   */
  public java.util.Set<com.floreantpos.model.PosTransaction> getTransactions() {
    return transactions;
  }

  /**
   * Set the value related to the column: transactions
   * 
   * @param transactions the transactions value
   */
  public void setTransactions(java.util.Set<com.floreantpos.model.PosTransaction> transactions) {
    this.transactions = transactions;
  }

  public void addTotransactions(com.floreantpos.model.PosTransaction posTransaction) {
    if (null == getTransactions())
      setTransactions(new java.util.TreeSet<com.floreantpos.model.PosTransaction>());
    getTransactions().add(posTransaction);
  }

  @Override
  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.Ticket))
      return false;
    else {
      com.floreantpos.model.Ticket ticket = (com.floreantpos.model.Ticket) obj;
      if (null == this.getId() || null == ticket.getId())
        return false;
      else
        return (this.getId().equals(ticket.getId()));
    }
  }

  @Override
  public int hashCode() {
    if (Integer.MIN_VALUE == this.hashCode) {
      if (null == this.getId())
        return super.hashCode();
      else {
        String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
        this.hashCode = hashStr.hashCode();
      }
    }
    return this.hashCode;
  }

  @Override
  public int compareTo(BaseTicket obj) {
    if (obj.hashCode() > hashCode())
      return 1;
    else if (obj.hashCode() < hashCode())
      return -1;
    else
      return 0;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public String getUniqId() {
    return uniqId;
  }

  public void setUniqId(String uniqId) {
    this.uniqId = uniqId;
  }

  public java.lang.Integer getDineInNumber() {
    return dineInNumber;
  }

  public void setDineInNumber(java.lang.Integer dineInNumber) {
    this.dineInNumber = dineInNumber;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public Double getReceivedAmount() {
    return receivedAmount;
  }

  public void setReceivedAmount(Double receivedAmount) {
    this.receivedAmount = receivedAmount;
  }

  public Double getChangeAmount() {
    return changeAmount;
  }

  public void setChangeAmount(Double changeAmount) {
    this.changeAmount = changeAmount;
  }

}
