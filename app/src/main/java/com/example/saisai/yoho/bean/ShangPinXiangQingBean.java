package com.example.saisai.yoho.bean;

import java.util.List;

/**
 * Created by saisai on 2016/9/1.
 */
public class ShangPinXiangQingBean {
    /**
     * sucessfully : ok
     * goods : [{"_id":"1","title":"奔跑吧兄弟 撕名牌魔术贴徽章T恤","price":"139.00","ShopId":"1","GoodsUrl":"http://item.yohobuy.com/product/pro_204497_273231/","time":"1439984566","discount":"139.00","SexId":"0","CategoryId":"0","ColorId":"0","advertId":"0"}]
     * img : [{"_id":"44","imgpath":"a1.jpg","goodsId":"1","UserId":null,"NewsId":null,"advertId":null,"shopId":"1"},{"_id":"45","imgpath":"a2.jpg","goodsId":"1","UserId":null,"NewsId":null,"advertId":null,"shopId":"1"},{"_id":"46","imgpath":"a3.jpg","goodsId":"1","UserId":null,"NewsId":null,"advertId":null,"shopId":"1"},{"_id":"47","imgpath":"a4.jpg","goodsId":"1","UserId":null,"NewsId":null,"advertId":null,"shopId":"1"},{"_id":"48","imgpath":"a5.jpg","goodsId":"1","UserId":null,"NewsId":null,"advertId":null,"shopId":"1"}]
     * imgvale : [{"_id":"1","imgpath":"v1.jpg","goodsId":"1"},{"_id":"2","imgpath":"v2.jpg","goodsId":"1"},{"_id":"3","imgpath":"v3.jpg","goodsId":"1"},{"_id":"4","imgpath":"v4.jpg","goodsId":"1"},{"_id":"5","imgpath":"v5.jpg","goodsId":"1"},{"_id":"6","imgpath":"v6.jpg","goodsId":"1"},{"_id":"7","imgpath":"v7.jpg","goodsId":"1"}]
     */

    private String sucessfully;
    private List<GoodsBean> goods;
    private List<ImgBean> img;
    private List<ImgvaleBean> imgvale;

    public void setSucessfully(String sucessfully) {
        this.sucessfully = sucessfully;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public void setImgvale(List<ImgvaleBean> imgvale) {
        this.imgvale = imgvale;
    }

    public String getSucessfully() {
        return sucessfully;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public List<ImgvaleBean> getImgvale() {
        return imgvale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShangPinXiangQingBean that = (ShangPinXiangQingBean) o;

        if (sucessfully != null ? !sucessfully.equals(that.sucessfully) : that.sucessfully != null)
            return false;
        if (goods != null ? !goods.equals(that.goods) : that.goods != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        return imgvale != null ? imgvale.equals(that.imgvale) : that.imgvale == null;

    }

    @Override
    public int hashCode() {
        int result = sucessfully != null ? sucessfully.hashCode() : 0;
        result = 31 * result + (goods != null ? goods.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (imgvale != null ? imgvale.hashCode() : 0);
        return result;
    }

    public static class GoodsBean {
        /**
         * _id : 1
         * title : 奔跑吧兄弟 撕名牌魔术贴徽章T恤
         * price : 139.00
         * ShopId : 1
         * GoodsUrl : http://item.yohobuy.com/product/pro_204497_273231/
         * time : 1439984566
         * discount : 139.00
         * SexId : 0
         * CategoryId : 0
         * ColorId : 0
         * advertId : 0
         */

        private String _id;
        private String title;
        private String price;
        private String ShopId;
        private String GoodsUrl;
        private String time;
        private String discount;
        private String SexId;
        private String CategoryId;
        private String ColorId;
        private String advertId;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setShopId(String ShopId) {
            this.ShopId = ShopId;
        }

        public void setGoodsUrl(String GoodsUrl) {
            this.GoodsUrl = GoodsUrl;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public void setSexId(String SexId) {
            this.SexId = SexId;
        }

        public void setCategoryId(String CategoryId) {
            this.CategoryId = CategoryId;
        }

        public void setColorId(String ColorId) {
            this.ColorId = ColorId;
        }

        public void setAdvertId(String advertId) {
            this.advertId = advertId;
        }

        public String get_id() {
            return _id;
        }

        public String getTitle() {
            return title;
        }

        public String getPrice() {
            return price;
        }

        public String getShopId() {
            return ShopId;
        }

        public String getGoodsUrl() {
            return GoodsUrl;
        }

        public String getTime() {
            return time;
        }

        public String getDiscount() {
            return discount;
        }

        public String getSexId() {
            return SexId;
        }

        public String getCategoryId() {
            return CategoryId;
        }

        public String getColorId() {
            return ColorId;
        }

        public String getAdvertId() {
            return advertId;
        }
    }

    public static class ImgBean {
        /**
         * _id : 44
         * imgpath : a1.jpg
         * goodsId : 1
         * UserId : null
         * NewsId : null
         * advertId : null
         * shopId : 1
         */

        private String _id;
        private String imgpath;
        private String goodsId;
        private Object UserId;
        private Object NewsId;
        private Object advertId;
        private String shopId;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public void setUserId(Object UserId) {
            this.UserId = UserId;
        }

        public void setNewsId(Object NewsId) {
            this.NewsId = NewsId;
        }

        public void setAdvertId(Object advertId) {
            this.advertId = advertId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String get_id() {
            return _id;
        }

        public String getImgpath() {
            return imgpath;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public Object getUserId() {
            return UserId;
        }

        public Object getNewsId() {
            return NewsId;
        }

        public Object getAdvertId() {
            return advertId;
        }

        public String getShopId() {
            return shopId;
        }
    }

    public static class ImgvaleBean {
        /**
         * _id : 1
         * imgpath : v1.jpg
         * goodsId : 1
         */

        private String _id;
        private String imgpath;
        private String goodsId;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String get_id() {
            return _id;
        }

        public String getImgpath() {
            return imgpath;
        }

        public String getGoodsId() {
            return goodsId;
        }
    }
}
