/**
 * 
 */
package pojo;

/**
 * @author priyank
 *
 */
public class Response {

	ResponseStatus status = ResponseStatus.SUCCESS;
    
    Object object;
    
    public ResponseStatus getStatus() {
      return status;
    }
    
    public void setStatus(ResponseStatus status) {
      this.status = status;
    }
    
    public Object getObject() {
      return object;
    }
    
    public void setObject(Object object) {
      this.object = object;
    }
    
    public void error() {
      this.setStatus(ResponseStatus.ERROR);
    }
}
