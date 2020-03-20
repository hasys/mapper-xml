package org.treblereel.gwt.jackson.api.deser.map.key;

import java.util.Date;

/**
 * <p>DateDeserializer interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface DateDeserializer<D extends Date> {

    /**
     * <p>deserializeMillis</p>
     *
     * @param millis a long.
     * @return a D object.
     */
    D deserializeMillis(long millis);

    /**
     * <p>deserializeDate</p>
     *
     * @param date a {@link java.util.Date} object.
     * @return a D object.
     */
    D deserializeDate(Date date);
}
