package com.shufa.easylayout.easylayout;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class Product {

    public List<Classify> classify;

    static public class Classify {
        public int type;
        public String title;
        List<Des> des;

        public Classify(int type, String title, List<Des> des) {
            this.type = type;
            this.title = title;
            this.des = des;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Des> getDes() {
            return des;
        }

        public void setDes(List<Des> des) {
            this.des = des;
        }

        public static class Des {
            int type;
            String content;

            public Des(int type, String content) {
                this.type = type;
                this.content = content;
            }
        }

    }

}
