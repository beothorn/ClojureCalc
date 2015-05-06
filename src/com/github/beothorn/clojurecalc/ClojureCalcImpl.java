package com.github.beothorn.clojurecalc;

import com.sun.star.lang.XLocalizable;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lib.uno.helper.WeakBase;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.uno.XComponentContext;
import com.sun.star.table.XCellRange;

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
 
    // org.openoffice.addin.XTestAddin:
    public int redouble(int nValue)
    {
        // TODO !!!
        // Exchange the default return implementation.
        // NOTE: Default initialized polymorphic structs can cause problems
        // because of missing default initialization of primitive types of
        // some C++ compilers or different Any initialization in Java and C++
        // polymorphic structs.
        return 0;
    }
 
    // com.sun.star.lang.XServiceInfo:
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
    
   public String clj(String exp)
   {
       return ClojureInterpreter.runClojure(exp);
   }
   
   public String clcol(XCellRange cells){
       return "On compile XCellRange on idl gives some error, will investigate";
   }
}
