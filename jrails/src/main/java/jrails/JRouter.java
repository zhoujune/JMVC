package jrails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.*;

public class JRouter {

    List<String> verbs = new ArrayList<String>();
    List<String> paths = new ArrayList<String>();
    List<Class> clazzs = new ArrayList<Class>();
    List<String> methods = new ArrayList<String>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        verbs.add(verb);
        paths.add(path);
        clazzs.add(clazz);
        methods.add(method);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        for (int i = 0; i < verbs.size(); i++) {
            if (verbs.get(i) == verb && paths.get(i) == path) {
                return clazzs.get(i).getName() + "#" + methods.get(i);
            }
        }
        return null;
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        for (int i = 0; i < verbs.size(); i++) {
            if (verbs.get(i) == verb && paths.get(i) == path) {

                try {
                    String meth = methods.get(i);
                    for (Method m : clazzs.get(i).getDeclaredMethods()) {
                        if (m.getName().equals(meth)) {
                            Object o = m.invoke(null, params);
                            return (Html) o;

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    throw new UnsupportedOperationException();
                }

            }
        }
        throw new UnsupportedOperationException();
    }
}
