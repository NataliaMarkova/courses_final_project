package ua.natalia_markova.project4.controllers;

import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;

/**
 * Created by natalia_markova on 23.07.2016.
 */
public interface RequestHandler {
    /**
     * Handles request that came from the client depending on request URI
     * @throws WrongRequestURIException if no method found to proceed the request URI
     * @param request RequestWrapper object
     * @param requestURI request path
     * @return the name of jsp-page or request URI
     */
    String handleRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException;
}
