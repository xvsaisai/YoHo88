package com.example.saisai.yoho.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/8/26.
 */
public class FllowItemBean {
    /**
     * sucessfully : ok
     * follow : [{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"1","price":"134","distance":"125","goodsimg":"a1.jpg"},{"_id":"2","price":"144","distance":"125","goodsimg":"a2.jpg"},{"_id":"3","price":"154","distance":"125","goodsimg":"a3.jpg"}]},{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"4","price":"134","distance":"125","goodsimg":"c1.jpg"},{"_id":"5","price":"144","distance":"115","goodsimg":"c2.jpg"},{"_id":"6","price":"154","distance":"105","goodsimg":"c3.jpg"}]},{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"7","price":"134","distance":"134","goodsimg":"d1.jpg"},{"_id":"8","price":"344","distance":"125","goodsimg":"d2.jpg"},{"_id":"9","price":"454","distance":"125","goodsimg":"d3.jpg"}]},{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"10","price":"134","distance":"125","goodsimg":"g1.jpg"},{"_id":"11","price":"244","distance":"125","goodsimg":"g2.jpg"},{"_id":"12","price":"254","distance":"125","goodsimg":"g3.jpg"}]},{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"13","price":"134","distance":"125","goodsimg":"h1.jpg"},{"_id":"14","price":"144","distance":"125","goodsimg":"h2.jpg"},{"_id":"15","price":"194","distance":"125","goodsimg":"h3.jpg"}]},{"brandname":"nike","brandimg":"brand1.jpg","goods":[{"_id":"16","price":"134","distance":"125","goodsimg":"accessories2.jpg"},{"_id":"17","price":"184","distance":"125","goodsimg":"accessories3.jpg"},{"_id":"18","price":"154","distance":"125","goodsimg":"accessories4.jpg"}]}]
     */

    private String sucessfully;
    private List<FollowBean> follow;

    public void setSucessfully(String sucessfully) {
        this.sucessfully = sucessfully;
    }

    public void setFollow(List<FollowBean> follow) {
        this.follow = follow;
    }

    public String getSucessfully() {
        return sucessfully;
    }

    public List<FollowBean> getFollow() {
        return follow;
    }

    public static class FollowBean {
        /**
         * brandname : nike
         * brandimg : brand1.jpg
         * goods : [{"_id":"1","price":"134","distance":"125","goodsimg":"a1.jpg"},{"_id":"2","price":"144","distance":"125","goodsimg":"a2.jpg"},{"_id":"3","price":"154","distance":"125","goodsimg":"a3.jpg"}]
         */

        private String brandname;
        private String brandimg;
        private List<GoodsBean> goods;

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public void setBrandimg(String brandimg) {
            this.brandimg = brandimg;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public String getBrandname() {
            return brandname;
        }

        public String getBrandimg() {
            return brandimg;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public static class GoodsBean implements Serializable {
            /**
             * _id : 1
             * price : 134
             * distance : 125
             * goodsimg : a1.jpg
             */

            private String _id;
            private String price;
            private String distance;
            private String goodsimg;

            public void set_id(String _id) {
                this._id = _id;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public void setGoodsimg(String goodsimg) {
                this.goodsimg = goodsimg;
            }

            public String get_id() {
                return _id;
            }

            public String getPrice() {
                return price;
            }

            public String getDistance() {
                return distance;
            }

            public String getGoodsimg() {
                return goodsimg;
            }
        }
    }
}
