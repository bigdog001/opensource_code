package com.bigdog.server.web.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/13/13
 * Time: 9:01 AM
 */
@WebService
public interface IBookService {
    @WebMethod
    public String getBook();
}
