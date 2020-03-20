/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.treblereel.gwt.jackson.api.deser.bean;

import org.treblereel.gwt.jackson.api.JacksonContextProvider;
import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.annotation.XMLTypeInfo;
import org.treblereel.gwt.jackson.api.annotation.XMLTypeInfo.As;
import org.treblereel.gwt.jackson.api.exception.XMLDeserializationException;
import org.treblereel.gwt.jackson.api.stream.XMLReader;
import org.treblereel.gwt.jackson.api.stream.XMLToken;

import java.util.*;
import java.util.Map.Entry;

import static org.treblereel.gwt.jackson.api.annotation.XMLTypeInfo.As.*;

/**
 * Base implementation of {@link XMLDeserializer} for beans.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractBeanXMLDeserializer<T> extends XMLDeserializer<T> implements InternalDeserializer<T,
        AbstractBeanXMLDeserializer<T>> {

    protected final InstanceBuilder<T> instanceBuilder;

    private final MapLike<BeanPropertyDeserializer<T, ?>> deserializers;

    private final MapLike<BackReferenceProperty<T, ?>> backReferenceDeserializers;

    private final Set<String> defaultIgnoredProperties;

    private final Set<String> requiredProperties;

    private final IdentityDeserializationInfo<T> defaultIdentityInfo;

    private final TypeDeserializationInfo<T> defaultTypeInfo;

    private final Map<Class, SubtypeDeserializer> subtypeClassToDeserializer;

    private final AnySetterDeserializer<T, ?> anySetterDeserializer;

    /**
     * <p>Constructor for AbstractBeanXMLDeserializer.</p>
     */
    protected AbstractBeanXMLDeserializer() {
        this.instanceBuilder = initInstanceBuilder();
        this.deserializers = initDeserializers();
        this.backReferenceDeserializers = initBackReferenceDeserializers();
        this.defaultIgnoredProperties = initIgnoredProperties();
        this.requiredProperties = initRequiredProperties();
        this.defaultIdentityInfo = initIdentityInfo();
        this.defaultTypeInfo = initTypeInfo();
        this.subtypeClassToDeserializer = initMapSubtypeClassToDeserializer();
        this.anySetterDeserializer = initAnySetterDeserializer();
    }

    /**
     * Initialize the {@link InstanceBuilder}. Returns null if the class isn't instantiable.
     *
     * @return a {@link InstanceBuilder} object.
     */
    protected InstanceBuilder<T> initInstanceBuilder() {
        return null;
    }

    /**
     * Initialize the {@link MapLike} containing the property deserializers. Returns an empty map if there are no properties to
     * deserialize.
     *
     * @return a {@link MapLike} object.
     */
    protected MapLike<BeanPropertyDeserializer<T, ?>> initDeserializers() {
        //Change by Ahmad Bawaneh, replace JSNI types with IsInterop types
        return JacksonContextProvider.get().mapLikeFactory().make();
    }

    /**
     * Initialize the {@link MapLike} containing the back reference deserializers. Returns an empty map if there are no back
     * reference on the bean.
     *
     * @return a {@link MapLike} object.
     */
    protected MapLike<BackReferenceProperty<T, ?>> initBackReferenceDeserializers() {
        return JacksonContextProvider.get().mapLikeFactory().make();
    }

    /**
     * Initialize the {@link java.util.Set} containing the ignored property names. Returns an empty set if there are no ignored properties.
     *
     * @return a {@link java.util.Set} object.
     */
    protected Set<String> initIgnoredProperties() {
        return Collections.emptySet();
    }

    /**
     * Initialize the {@link java.util.Set} containing the required property names. Returns an empty set if there are no required properties.
     *
     * @return a {@link java.util.Set} object.
     */
    protected Set<String> initRequiredProperties() {
        return Collections.emptySet();
    }

    /**
     * Initialize the {@link IdentityDeserializationInfo}.
     *
     * @return a {@link IdentityDeserializationInfo} object.
     */
    protected IdentityDeserializationInfo<T> initIdentityInfo() {
        return null;
    }

    /**
     * Initialize the {@link TypeDeserializationInfo}.
     *
     * @return a {@link TypeDeserializationInfo} object.
     */
    protected TypeDeserializationInfo<T> initTypeInfo() {
        return null;
    }

    /**
     * Initialize the {@link java.util.Map} containing the {@link SubtypeDeserializer}. Returns an empty map if the bean has no subtypes.
     *
     * @return a {@link java.util.Map} object.
     */
    protected Map<Class, SubtypeDeserializer> initMapSubtypeClassToDeserializer() {
        return Collections.emptyMap();
    }

    /**
     * Initialize the {@link AnySetterDeserializer}.
     *
     * @return a {@link AnySetterDeserializer} object.
     */
    protected AnySetterDeserializer<T, ?> initAnySetterDeserializer() {
        return null;
    }

    /**
     * Whether encountering of unknown
     * properties should result in a failure (by throwing a
     * {@link XMLDeserializationException}) or not.
     *
     * @return a boolean.
     */
    protected boolean isDefaultIgnoreUnknown() {
        return false;
    }

    /**
     * <p>getDeserializedType</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public abstract Class getDeserializedType();

    /** {@inheritDoc} */
    @Override
    public T doDeserialize(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {

        // Processing the parameters. We fallback to default if parameter is not present.
        final IdentityDeserializationInfo identityInfo = null == params.getIdentityInfo() ? defaultIdentityInfo : params.getIdentityInfo();
        final TypeDeserializationInfo typeInfo = null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();

        XMLToken token = reader.peek();

        // If it's not a json object or array, it must be an identifier
        if (null != identityInfo && !XMLToken.BEGIN_OBJECT.equals(token) && !XMLToken.BEGIN_ARRAY.equals(token)) {
            Object id;
            if (identityInfo.isProperty()) {
                HasDeserializerAndParameters propertyDeserializer = deserializers.get(identityInfo.getPropertyName());
                if (null == propertyDeserializer) {
                    propertyDeserializer = instanceBuilder.getParametersDeserializer().get(identityInfo.getPropertyName());
                }
                id = propertyDeserializer.getDeserializer().deserialize(reader, ctx);
            } else {
                id = identityInfo.readId(reader, ctx);
            }
            Object instance = ctx.getObjectWithId(identityInfo.newIdKey(id));
            if (null == instance) {
                throw ctx.traceError("Cannot find an object with id " + id, reader);
            }
            return (T) instance;
        }

        T result;

        if (null != typeInfo) {

            As include;
            if (XMLToken.BEGIN_ARRAY.equals(token)) {
                // we can have a wrapper array even if the user specified As.PROPERTY in some cases (enum, creator delegation)
                include = WRAPPER_ARRAY;
            } else {
                include = typeInfo.getInclude();
            }

            switch (include) {
                case PROPERTY:
                    // the type info is the first property of the object
                    reader.beginObject();
                    Map<String, String> bufferedProperties = null;
                    String typeInfoProperty = null;
                    while (XMLToken.NAME.equals(reader.peek())) {
                        String name = reader.nextName();

                        if (typeInfo.getPropertyName().equals(name)) {
                            typeInfoProperty = reader.nextString();
                            break;
                        } else {
                            if (null == bufferedProperties) {
                                bufferedProperties = new HashMap<String, String>();
                            }
                            bufferedProperties.put(name, reader.nextValue());
                        }
                    }
                    if (null == typeInfoProperty) {
                        throw ctx.traceError("Cannot find the property " + typeInfo
                                .getPropertyName() + " containing the type information", reader);
                    }
                    result = getDeserializer(reader, ctx, typeInfo, typeInfoProperty)
                            .deserializeInline(reader, ctx, params, identityInfo, typeInfo, typeInfoProperty, bufferedProperties);
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    result = getDeserializer(reader, ctx, typeInfo, typeInfoWrapObj)
                            .deserializeWrapped(reader, ctx, params, identityInfo, typeInfo, typeInfoWrapObj);
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    result = getDeserializer(reader, ctx, typeInfo, typeInfoWrapArray)
                            .deserializeWrapped(reader, ctx, params, identityInfo, typeInfo, typeInfoWrapArray);
                    reader.endArray();
                    break;

                default:
                    throw ctx.traceError("JsonTypeInfo.As." + typeInfo.getInclude() + " is not supported", reader);
            }
        } else if (canDeserialize()) {
            result = deserializeWrapped(reader, ctx, params, identityInfo, null, null);
        } else {
            throw ctx.traceError("Cannot instantiate the type " + getDeserializedType().getName(), reader);
        }

        return result;
    }

    /**
     * <p>canDeserialize</p>
     *
     * @return a boolean.
     */
    protected boolean canDeserialize() {
        return null != instanceBuilder;
    }

    /** {@inheritDoc} */
    @Override
    public T deserializeWrapped(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params,
                                IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation) {
        reader.beginObject();
        T result = deserializeInline(reader, ctx, params, identityInfo, typeInfo, typeInformation, null);
        reader.endObject();
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Deserializes all the properties of the bean. The {@link XMLReader} must be in a json object.
     */
    @Override
    public final T deserializeInline(final XMLReader reader, final XMLDeserializationContext ctx, XMLDeserializerParameters params,
                                     IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String type,
                                     Map<String, String> bufferedProperties) {
        final boolean ignoreUnknown = params.isIgnoreUnknown() || isDefaultIgnoreUnknown();
        final Set<String> ignoredProperties;
        if (null == params.getIgnoredProperties()) {
            ignoredProperties = defaultIgnoredProperties;
        } else {
            ignoredProperties = new HashSet<String>(defaultIgnoredProperties);
            ignoredProperties.addAll(params.getIgnoredProperties());
        }

        // we will remove the properties read from this list and check at the end it's empty
        Set<String> requiredPropertiesLeft = requiredProperties.isEmpty() ? Collections
                .<String>emptySet() : new HashSet<String>(requiredProperties);

        // we first look for identity. It can also buffer properties if it is not in current reader position.
        Object id = null;
        Map<String, Object> bufferedPropertiesValues = null;
        if (null != identityInfo) {
            XMLReader identityReader = null;

            // we look if it has not been already buffered
            String propertyValue = null;

            // we fisrt look if the identity property has not been read already
            if (null != bufferedProperties) {
                propertyValue = bufferedProperties.remove(identityInfo.getPropertyName());
            }

            if (null != propertyValue) {
                identityReader = ctx.newXMLReader(propertyValue);
            } else {
                // we search for the identity property
                while (XMLToken.NAME.equals(reader.peek())) {
                    String name = reader.nextName();

                    if (ignoredProperties.contains(name)) {
                        reader.skipValue();
                        continue;
                    }

                    if (identityInfo.getPropertyName().equals(name)) {
                        identityReader = reader;
                        break;
                    } else {
                        if (null == bufferedProperties) {
                            bufferedProperties = new HashMap<String, String>();
                        }
                        bufferedProperties.put(name, reader.nextValue());
                    }
                }
            }

            if (null != identityReader) {
                if (identityInfo.isProperty()) {
                    HasDeserializerAndParameters propertyDeserializer = deserializers.get(identityInfo.getPropertyName());
                    if (null == propertyDeserializer) {
                        // the identity property is defined in constructor
                        propertyDeserializer = instanceBuilder.getParametersDeserializer().get(identityInfo.getPropertyName());
                        id = propertyDeserializer.getDeserializer().deserialize(identityReader, ctx);
                        bufferedPropertiesValues = new HashMap<String, Object>(1);
                        bufferedPropertiesValues.put(identityInfo.getPropertyName(), id);
                    } else {
                        id = propertyDeserializer.getDeserializer().deserialize(identityReader, ctx);
                    }
                } else {
                    id = identityInfo.readId(identityReader, ctx);
                }
            }
        }

        // we first instantiate the bean. It might buffer properties if there are properties required for constructor and they are not in
        // first position
        Instance<T> instance = instanceBuilder.newInstance(reader, ctx, params, bufferedProperties, bufferedPropertiesValues);

        T bean = instance.getInstance();
        bufferedProperties = instance.getBufferedProperties();

        // we save the instance if we have an id
        if (null != id) {
            if (identityInfo.isProperty()) {
                BeanPropertyDeserializer propertyDeserializer = deserializers.get(identityInfo.getPropertyName());
                if (null != propertyDeserializer) {
                    propertyDeserializer.setValue(bean, id, ctx);
                }
            }
            ctx.addObjectId(identityInfo.newIdKey(id), bean);
        }

        // we flush any buffered properties
        flushBufferedProperties(bean, bufferedProperties, requiredPropertiesLeft, ctx, ignoreUnknown, ignoredProperties);

        // in case there is a property that need the type info
        if (null != typeInfo && null != typeInfo.getPropertyName() && null != type) {
            BeanPropertyDeserializer deserializer = getPropertyDeserializer(typeInfo.getPropertyName(), ctx, true);
            if (null != deserializer) {
                deserializer.setValue(bean, type, ctx);
            }
        }

        while (XMLToken.NAME.equals(reader.peek())) {
            String propertyName = reader.nextName();

            requiredPropertiesLeft.remove(propertyName);

            if (ignoredProperties.contains(propertyName)) {
                reader.skipValue();
                continue;
            }

            BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer(propertyName, ctx, ignoreUnknown);
            if (null != property) {
                property.deserialize(reader, bean, ctx);
            } else if (null != anySetterDeserializer) {
                anySetterDeserializer.deserialize(reader, bean, propertyName, ctx);
            } else {
                reader.skipValue();
            }
        }

        if (!requiredPropertiesLeft.isEmpty()) {
            throw ctx.traceError("Required properties are missing : " + requiredPropertiesLeft, reader);
        }
        return bean;
    }

    private void flushBufferedProperties(T bean, Map<String, String> bufferedProperties, Set<String> requiredPropertiesLeft,
                                         XMLDeserializationContext ctx, boolean ignoreUnknown, Set<String> ignoredProperties) {
        if (null != bufferedProperties && !bufferedProperties.isEmpty()) {
            for (Entry<String, String> bufferedProperty : bufferedProperties.entrySet()) {
                String propertyName = bufferedProperty.getKey();

                requiredPropertiesLeft.remove(propertyName);

                if (ignoredProperties.contains(propertyName)) {
                    continue;
                }

                BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer(propertyName, ctx, ignoreUnknown);
                if (null != property) {
                    property.deserialize(ctx.newXMLReader(bufferedProperty.getValue()), bean, ctx);
                } else if (null != anySetterDeserializer) {
                    anySetterDeserializer.deserialize(ctx.newXMLReader(bufferedProperty.getValue()), bean, propertyName, ctx);
                }
            }
        }
    }

    private BeanPropertyDeserializer<T, ?> getPropertyDeserializer(String propertyName, XMLDeserializationContext ctx, boolean
            ignoreUnknown) {
        BeanPropertyDeserializer<T, ?> property = deserializers.get(propertyName);
        if (null == property) {
            if (!ignoreUnknown && ctx.isFailOnUnknownProperties() && null == anySetterDeserializer) {
                throw ctx.traceError("Unknown property '" + propertyName + "' in (de)serializer "+this.getClass().getCanonicalName());
            }
        }
        return property;
    }

    private InternalDeserializer<T, ? extends XMLDeserializer<T>> getDeserializer(XMLReader reader, XMLDeserializationContext ctx,
                                                                                   TypeDeserializationInfo typeInfo, String
                                                                                           typeInformation) {
        Class typeClass = typeInfo.getTypeClass(typeInformation);
        if (null == typeClass) {
            throw ctx.traceError("Could not find the type associated to " + typeInformation, reader);
        }

        return getDeserializer(reader, ctx, typeClass);
    }

    private InternalDeserializer<T, ? extends XMLDeserializer<T>> getDeserializer(XMLReader reader, XMLDeserializationContext ctx,
                                                                                   Class typeClass) {
        if (typeClass == getDeserializedType()) {
            return this;
        }

        SubtypeDeserializer deserializer = subtypeClassToDeserializer.get(typeClass);
        if (null == deserializer) {
            throw ctx.traceError("No deserializer found for the type " + typeClass.getName(), reader);
        }
        return deserializer;
    }

    /** {@inheritDoc} */
    @Override
    public AbstractBeanXMLDeserializer<T> getDeserializer() {
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void setBackReference(String referenceName, Object reference, T value, XMLDeserializationContext ctx) {
        if (null == value) {
            return;
        }

        XMLDeserializer<T> deserializer = getDeserializer(null, ctx, value.getClass()).getDeserializer();
        if (deserializer.getClass() != getClass()) {
            // we test if it's not this deserializer to avoid an infinite loop
            deserializer.setBackReference(referenceName, reference, value, ctx);
            return;
        }

        BackReferenceProperty backReferenceProperty = backReferenceDeserializers.get(referenceName);
        if (null == backReferenceProperty) {
            throw ctx.traceError("The back reference '" + referenceName + "' does not exist");
        }
        backReferenceProperty.setBackReference(value, reference, ctx);
    }
}
