
package com.connectme.domain.triplived;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Response {

    @Expose
    private Integer numFound;
    @Expose
    private Integer start;
    @Expose
    private List<Doc> docs = new ArrayList<Doc>();

    /**
     * 
     * @return
     *     The numFound
     */
    public Integer getNumFound() {
        return numFound;
    }

    /**
     * 
     * @param numFound
     *     The numFound
     */
    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    /**
     * 
     * @return
     *     The start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * 
     * @return
     *     The docs
     */
    public List<Doc> getDocs() {
        return docs;
    }

    /**
     * 
     * @param docs
     *     The docs
     */
    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

}
