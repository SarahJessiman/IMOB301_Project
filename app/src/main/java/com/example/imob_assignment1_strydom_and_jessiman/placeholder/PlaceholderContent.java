package com.example.imob_assignment1_strydom_and_jessiman.placeholder;

import com.example.imob_assignment1_strydom_and_jessiman.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderContent {
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    static {
        addItem(new PlaceholderItem("1", "Create Student Records", "create_student_record"));
        addItem(new PlaceholderItem("2", "Create Module Records", "create_module_record"));
        addItem(new PlaceholderItem("3", "Create Instructor Records", "create_instructor_record"));
    }

    public static class PlaceholderItem {
        public final String id;

        public final String screenName;

        public final String screenFile;

        public PlaceholderItem(String id, String screenName, String screenFile) {
            this.id = id;
            this.screenName = screenName;
            this.screenFile = screenFile;
        }

        @Override
        public String toString() {
            return screenName;
        }
    }
}