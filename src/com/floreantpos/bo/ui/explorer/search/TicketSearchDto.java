package com.floreantpos.bo.ui.explorer.search;

import java.util.Date;

import com.floreantpos.bo.ui.ComboOption;

/**
 * Ticket search dto.
 * 
 * @author Destiny
 *
 */
public class TicketSearchDto {

  public static final int TICKET_TYPE_ALL = 0;
  public static final int TICKET_TYPE_DINE_IN = 1;
  public static final int TICKET_TYPE_TAKE_OUT = 2;
  public static final int TICKET_TYPE_HOME_DELIVERY = 3;

  public static final int MEMBERSHIP_ALL = 0;
  public static final int MEMBERSHIP_MEMBER = 1;
  public static final int MEMBERSHIP_NON_MEMBER = 2;

  private String uniqIdOrPhone;

  private Date startDate;
  private Date endDate;

  private boolean allTickets;
  private boolean paidTickets;
  private boolean voidedTickets;
  private boolean refundedTickets;

  private ComboOption ticketType;
  private ComboOption membershipType;

  public String getUniqIdOrPhone() {
    return uniqIdOrPhone;
  }

  public void setUniqIdOrPhone(String uniqIdOrPhone) {
    this.uniqIdOrPhone = uniqIdOrPhone;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isAllTickets() {
    return allTickets;
  }

  public void setAllTickets(boolean allTickets) {
    this.allTickets = allTickets;
  }

  public boolean isPaidTickets() {
    return paidTickets;
  }

  public void setPaidTickets(boolean paidTickets) {
    this.paidTickets = paidTickets;
  }

  public boolean isVoidedTickets() {
    return voidedTickets;
  }

  public void setVoidedTickets(boolean voidedTickets) {
    this.voidedTickets = voidedTickets;
  }

  public boolean isRefundedTickets() {
    return refundedTickets;
  }

  public void setRefundedTickets(boolean refundedTickets) {
    this.refundedTickets = refundedTickets;
  }

  public ComboOption getTicketType() {
    return ticketType;
  }

  public void setTicketType(ComboOption ticketType) {
    this.ticketType = ticketType;
  }

  public ComboOption getMembershipType() {
    return membershipType;
  }

  public void setMembershipType(ComboOption membershipType) {
    this.membershipType = membershipType;
  }
}
