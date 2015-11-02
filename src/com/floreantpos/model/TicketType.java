package com.floreantpos.model;

public enum TicketType {
  DINE_IN("堂食"), TAKE_OUT("外带"), PICKUP("自取"), HOME_DELIVERY("外送"), DRIVE_THRU("穿越"), BAR_TAB("吧台");

  private String name;

  private TicketType(String value) {
    this.name = value;
  }

  public String toString() {
    return name().replaceAll("_", " ");
  }

  public String getValue() {
    return name;
  };

  public static TicketType fromDisplayName(String name) {
    switch (name) {
      case "堂食":
        return TicketType.DINE_IN;
      case "外带":
        return TicketType.TAKE_OUT;
      case "自取":
        return TicketType.PICKUP;
      case "外送":
        return TicketType.HOME_DELIVERY;
      case "穿越":
        return TicketType.DRIVE_THRU;
      case "吧台":
        return TicketType.BAR_TAB;
      default:
        return TicketType.DINE_IN;
    }
  }
}
