package com.example.sword.smartbutler.entity;

import java.util.List;

/**
 * 快递单号查询实体类
 * Created by sword on 2017/5/27.
 */

public class CourierData {

    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"company":"京东快递","com":"jd","no":"57376073162","status":"0","list":[{"datetime":"2017-05-27 03:00:00","remark":"分拣验货 【巴南区】 您的订单已到达京东【重庆巴南分拨中心】","zone":""},{"datetime":"2017-05-27 03:00:59","remark":"分拣 【巴南区】 您的订单在京东【重庆巴南分拨中心】分拣完成","zone":""},{"datetime":"2017-05-27 03:01:29","remark":"分拣发货 【巴南区】 您的订单在京东【重庆巴南分拨中心】发货完成，准备送往京东【北部新区站】","zone":""}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * company : 京东快递
         * com : jd
         * no : 57376073162
         * status : 0
         * list : [{"datetime":"2017-05-27 03:00:00","remark":"分拣验货 【巴南区】 您的订单已到达京东【重庆巴南分拨中心】","zone":""},{"datetime":"2017-05-27 03:00:59","remark":"分拣 【巴南区】 您的订单在京东【重庆巴南分拨中心】分拣完成","zone":""},{"datetime":"2017-05-27 03:01:29","remark":"分拣发货 【巴南区】 您的订单在京东【重庆巴南分拨中心】发货完成，准备送往京东【北部新区站】","zone":""}]
         */

        private String company;
        private String com;
        private String no;
        private String status;
        private List<ListBean> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * datetime : 2017-05-27 03:00:00
             * remark : 分拣验货 【巴南区】 您的订单已到达京东【重庆巴南分拨中心】
             * zone :
             */

            private String datetime;
            private String remark;
            private String zone;

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}
