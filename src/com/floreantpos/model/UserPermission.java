package com.floreantpos.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.floreantpos.POSConstants;
import com.floreantpos.model.base.BaseUserPermission;


@XmlRootElement(name = "user-permission")
public class UserPermission extends BaseUserPermission {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public UserPermission() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public UserPermission(int value, java.lang.String name) {
    super(value, name);
  }

  /* [CONSTRUCTOR MARKER END] */

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof UserPermission)) {
      return false;
    }

    UserPermission p = (UserPermission) obj;

    return this.getName().equalsIgnoreCase(p.getName());
  }

  @Override
  public String toString() {
    return getName();
  }

  public final static UserPermission VIEW_ALL_OPEN_TICKETS =
      new UserPermission(1, POSConstants.USER_PERM_VIEW_ALL_OPEN_TICKETS);
  public final static UserPermission CREATE_TICKET =
      new UserPermission(2, POSConstants.USER_PERM_CREATE_NEW_TICKET);
  public final static UserPermission EDIT_TICKET =
      new UserPermission(3, POSConstants.USER_PERM_EDIT_TICKET);
  public final static UserPermission SETTLE_TICKET =
      new UserPermission(4, POSConstants.USER_PERM_SETTLE_TICKET);
  public final static UserPermission ADD_DISCOUNT =
      new UserPermission(5, POSConstants.USER_PERM_ADD_DISCOUNT);
  public final static UserPermission VOID_TICKET =
      new UserPermission(6, POSConstants.USER_PERM_VOID_TICKET);
  public final static UserPermission REOPEN_TICKET =
      new UserPermission(7, POSConstants.USER_PERM_REOPEN_TICKET);
  public final static UserPermission PAY_OUT =
      new UserPermission(8, POSConstants.USER_PERM_PAY_OUT);
  public final static UserPermission REFUND_TICKET =
      new UserPermission(9, POSConstants.USER_PERM_REFUND);
  public final static UserPermission SHUT_DOWN =
      new UserPermission(10, POSConstants.USER_PERM_SHUT_DOWN);
  public final static UserPermission VIEW_BACK_OFFICE =
      new UserPermission(11, POSConstants.USER_PERM_VIEW_BACK_OFFICE);
  public final static UserPermission VIEW_ADMINISTRATIVE_TASK =
      new UserPermission(12, POSConstants.USER_PERM_VIEW_ADMINISTRATIVE_TASK);
  public final static UserPermission VIEW_EXPLORERS =
      new UserPermission(13, POSConstants.USER_PERM_VIEW_EXPLORERS);
  public final static UserPermission VIEW_REPORTS =
      new UserPermission(14, POSConstants.USER_PERM_VIEW_REPORTS);

  public final static UserPermission[] permissions =
      new UserPermission[] {VIEW_ALL_OPEN_TICKETS, CREATE_TICKET, EDIT_TICKET, SETTLE_TICKET,
          ADD_DISCOUNT, VOID_TICKET, REOPEN_TICKET, PAY_OUT, REFUND_TICKET, SHUT_DOWN,
          VIEW_BACK_OFFICE, VIEW_ADMINISTRATIVE_TASK, VIEW_EXPLORERS, VIEW_REPORTS};
}
