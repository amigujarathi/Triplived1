
package com.connectme.domain.triplived;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ResponseHeader {

    @Expose
    private Integer status;
    @Expose
    private Integer QTime;
    @Expose
    private Params params;

    /**
     * 
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The QTime
     */
    public Integer getQTime() {
        return QTime;
    }

    /**
     * 
     * @param QTime
     *     The QTime
     */
    public void setQTime(Integer QTime) {
        this.QTime = QTime;
    }

    /**
     * 
     * @return
     *     The params
     */
    public Params getParams() {
        return params;
    }

    /**
     * 
     * @param params
     *     The params
     */
    public void setParams(Params params) {
        this.params = params;
    }

}
