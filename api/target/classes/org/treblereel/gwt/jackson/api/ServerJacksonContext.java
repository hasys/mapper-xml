package org.treblereel.gwt.jackson.api;

import org.treblereel.gwt.jackson.api.deser.bean.DefaultMapLike;
import org.treblereel.gwt.jackson.api.utils.DefaultDateFormat;

/**
 * <p>ServerJacksonContext class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class ServerJacksonContext extends JsJacksonContext{

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public JacksonContext.DateFormat dateFormat() {
        return new DefaultDateFormat();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public MapLikeFactory mapLikeFactory() {
        return DefaultMapLike::new;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public XMLSerializerParameters defaultSerializerParameters() {
        return ServerJacksonXMLSerializerParameters.DEFAULT;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public XMLDeserializerParameters defaultDeserializerParameters() {
        return ServerJacksonXMLDeserializerParameters.DEFAULT;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public XMLSerializerParameters newSerializerParameters() {
        return new ServerJacksonXMLSerializerParameters();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public XMLDeserializerParameters newDeserializerParameters() {
        return new ServerJacksonXMLDeserializerParameters();
    }

}