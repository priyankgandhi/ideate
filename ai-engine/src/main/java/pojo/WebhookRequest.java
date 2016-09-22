package pojo;

import java.util.Date;

import ai.api.model.Result;
import ai.api.model.Status;

import com.google.gson.annotations.SerializedName;

public class WebhookRequest {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Unique identifier of the result.
     */
    @SerializedName("id")
    private String id;


    /**
     * Result object
     */
    @SerializedName("result")
    private Result result;

    @SerializedName("status")
    private Status status;

    /**
     * Unique identifier of the result.
     */
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }


    /**
     * Result object
     */
    public Result getResult() {
        return result;
    }

    public void setResult(final Result result) {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public boolean isError() {
        if (status != null && status.getCode() != null && status.getCode() >= 400) {
            return true;
        }

        return false;
    }

}

