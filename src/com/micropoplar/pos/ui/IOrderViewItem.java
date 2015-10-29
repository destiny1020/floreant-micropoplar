package com.micropoplar.pos.ui;

/**
 * MenuItemSet and MenuItem implement this interface to make them selectable in the order view.
 * 
 * @author destiny1020
 *
 */
public interface IOrderViewItem {

  byte[] getImage();

  Boolean isShowImageOnly();

  String getName();
}
