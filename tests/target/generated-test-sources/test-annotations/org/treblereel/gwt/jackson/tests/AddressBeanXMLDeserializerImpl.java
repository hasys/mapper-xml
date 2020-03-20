package org.treblereel.gwt.jackson.tests;

import org.treblereel.gwt.jackson.api.JacksonContextProvider;
import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.deser.bean.AbstractBeanXMLDeserializer;
import org.treblereel.gwt.jackson.api.deser.bean.BeanPropertyDeserializer;
import org.treblereel.gwt.jackson.api.deser.bean.HasDeserializerAndParameters;
import org.treblereel.gwt.jackson.api.deser.bean.Instance;
import org.treblereel.gwt.jackson.api.deser.bean.MapLike;
import org.treblereel.gwt.jackson.api.deser.bean.InstanceBuilder;
import org.treblereel.gwt.jackson.api.stream.XMLReader;
import org.treblereel.gwt.jackson.tests.Address;

public class AddressBeanXMLDeserializerImpl extends AbstractBeanXMLDeserializer<Address> {

    @Override()
    public Class getDeserializedType() {
        return Address.class;
    }

    @Override()
    protected MapLike<BeanPropertyDeserializer<Address, ?>> initDeserializers() {
    }
}