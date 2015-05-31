package com.github.beothorn.clojurecalc;

import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.lang.XLocalizable;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lib.uno.helper.WeakBase;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.table.XCellRange;
import com.sun.star.uno.XComponentContext;
import java.util.List;

public class ClojureCalcImpl extends WeakBase implements XServiceInfo, XLocalizable, XClojureCalc{
    
    private final XComponentContext m_xContext;
    private static final String m_implementationName = ClojureCalcImpl.class.getName();
    private static final String[] m_serviceNames = {"com.github.beothorn.clojurecalc.ClojureCalc" };
 
    private com.sun.star.lang.Locale m_locale = new com.sun.star.lang.Locale();
 
    public ClojureCalcImpl( XComponentContext context )
    {
        m_xContext = context;
    }
 
    public static XSingleComponentFactory __getComponentFactory(String sImplementationName ) { 
        if ( sImplementationName.equals( m_implementationName ) )
            return Factory.createComponentFactory(ClojureCalcImpl.class, m_serviceNames);
        return null;
    }
 
    public static boolean __writeRegistryServiceInfo(XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName, m_serviceNames, xRegistryKey);
    }

    public String getImplementationName() {
         return m_implementationName;
    }
 
    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;
 
        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }
 
    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }
 
    // com.sun.star.lang.XLocalizable:
    public void setLocale(com.sun.star.lang.Locale eLocale)
    {
        m_locale = eLocale;
    }
 
    public com.sun.star.lang.Locale getLocale()
    {
        return m_locale;
    }
    
   public String cljEval(String exp)
   {
       return ClojureInterpreter.runClojure(exp);
   }
   
   public String c(String exp)
   {
       if(exp.trim().equals("")) return "";
       return ClojureInterpreter.runClojure("("+exp+")");
   }
   
   public String cljn(String exp, Object[] any)
   {
       if(exp.trim().equals("")) return "";
       String result = exp;
       for (int i = 0; i < any.length; i++) {
           String replaceValue = "ERROR";
           if(any[i] instanceof Double){
               replaceValue = any[i].toString();
           }
           if(any[i] instanceof String){
               return "ERROR: Value is not a number at "+i;
           }
           if(any[i] instanceof Object[][]){
               replaceValue = ClojureInterpreter.toClojureCollectionNumber((Object[][])any[i]);
           }
           result = result.replaceAll("\\{"+i+"\\}", replaceValue);
       }
       return c(result);
   }
   
   public String cljs(String exp, Object[] any)
   {
       if(exp.trim().equals("")) return "";
       String result = exp;
       for (int i = 0; i < any.length; i++) {
           String replaceValue = "ERROR: Unknown type at "+i;
           if(any[i] instanceof Double || any[i] instanceof String){
               replaceValue = "'''"+any[i]+"'''";
           }
           if(any[i] instanceof Object[][]){
               replaceValue = ClojureInterpreter.toClojureCollectionString((Object[][])any[i]);
           }
           result = result.replaceAll("\\{"+i+"\\}", replaceValue);
       }
       return c(result);
   }
   
   public String cljToRange(String collection, String title, XCellRange cells){
        try {
            final List<List<String>> listOfListsOfValues = ClojureInterpreter.fromClojureCollectionStringToList(collection);
            for (int lineIndex = 0; lineIndex < listOfListsOfValues.size(); lineIndex++) {
                final List<String> line = listOfListsOfValues.get(lineIndex);
                for (int column = 0; column < line.size(); column++) {
                    final String value = line.get(column);
                    cells.getCellByPosition(column, lineIndex).setFormula(value);
                }
            }
            return title;
        } catch (IndexOutOfBoundsException ex) {
            return "Cell Range is too small for values";
        } catch (Exception e){
            return e.getMessage();
        }
   }
}
