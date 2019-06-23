package com.example.desk.ui.videolist.visibility.scroll;


import com.example.desk.ui.videolist.visibility.items.ListItem;

/**
 * @author Wayne
 */
public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
