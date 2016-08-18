/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc9959;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
/**
 *
 * @author Navneet
 */
@WebService
@Stateless
public class bc9959ServiceBean {
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "convert")
   
    public Double convert(String data,String convertrad)
    {    
        Double result=0.0;
        final Double fm=0.3048;
     final Double gl=3.78541178 ;
        Integer d=Integer.parseInt(data);
        
    if(convertrad.equals("ftomtr"))
    {
        
        result= d*(fm);
        return result;
       }
    
    if(convertrad.equals("gtoltr"))
    {
        result= d*(gl);
        return result;
    }
  return result;
    } 
}
