package authentication.entities;

public class ResponseObj {
    private String result;
    private int statusCode;

    public ResponseObj(String result, int statusCode) {
        this.result = result;
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
