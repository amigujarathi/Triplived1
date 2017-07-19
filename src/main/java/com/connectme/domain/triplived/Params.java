
package com.connectme.domain.triplived;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Params {

    @Expose
    private String indent;
    @Expose
    private String q;
    @Expose
    private String wt;

    /**
     * 
     * @return
     *     The indent
     */
    public String getIndent() {
        return indent;
    }

    /**
     * 
     * @param indent
     *     The indent
     */
    public void setIndent(String indent) {
        this.indent = indent;
    }

    /**
     * 
     * @return
     *     The q
     */
    public String getQ() {
        return q;
    }

    /**
     * 
     * @param q
     *     The q
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * 
     * @return
     *     The wt
     */
    public String getWt() {
        return wt;
    }

    /**
     * 
     * @param wt
     *     The wt
     */
    public void setWt(String wt) {
        this.wt = wt;
    }

}
