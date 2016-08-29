package com.example.saisai.yoho.bean;

import java.util.List;

/**
 * Created by admin on 2016/8/24.
 */
public class PinLeiLifeStyleBean {

    /**
     * sucessfully : ok
     * life : [{"_id":"21","name":"数码3c","SexId":"2"},{"_id":"22","name":"居家","SexId":"2"},{"_id":"23","name":"玩具娱乐","SexId":"2"},{"_id":"24","name":"文件","SexId":"2"},{"_id":"25","name":"美妆","SexId":"2"}]
     */

    private String sucessfully;
    /**
     * _id : 21
     * name : 数码3c
     * SexId : 2
     */

    private List<LifeBean> life;

    public String getSucessfully() {
        return sucessfully;
    }

    public void setSucessfully(String sucessfully) {
        this.sucessfully = sucessfully;
    }

    public List<LifeBean> getLife() {
        return life;
    }

    public void setLife(List<LifeBean> life) {
        this.life = life;
    }

    public static class LifeBean {
        private String _id;
        private String name;
        private String SexId;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSexId() {
            return SexId;
        }

        public void setSexId(String SexId) {
            this.SexId = SexId;
        }
    }
}
