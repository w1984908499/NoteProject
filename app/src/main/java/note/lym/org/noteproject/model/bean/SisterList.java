package note.lym.org.noteproject.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 *  漂亮姐姐总分类
 * @author yaoming.li
 * @since 2017-05-12 17:07
 */
public class SisterList extends DataSupport implements Serializable {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"ret_code":0,"ret_message":"Success","data":[{"id":1,"title":"清纯","status":1},{"id":2,"title":"性感","status":1},{"id":3,"title":"气质","status":1},{"id":4,"title":"唯美","status":1}]}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * ret_message : Success
         * data : [{"id":1,"title":"清纯","status":1},{"id":2,"title":"性感","status":1},{"id":3,"title":"气质","status":1},{"id":4,"title":"唯美","status":1}]
         */

        private int ret_code;
        private String ret_message;
        private List<DataBean> data;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public String getRet_message() {
            return ret_message;
        }

        public void setRet_message(String ret_message) {
            this.ret_message = ret_message;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * title : 清纯
             * status : 1
             */

            private int id;
            private String title;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
