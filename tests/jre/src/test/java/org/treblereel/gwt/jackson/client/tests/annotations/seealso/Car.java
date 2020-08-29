/*
 * Copyright © 2020 Treblereel
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

package org.treblereel.gwt.jackson.client.tests.annotations.seealso;

import javax.xml.bind.annotation.XmlRootElement;

/** @author Dmitrii Tikhomirov Created by treblereel 8/28/20 */
@XmlRootElement
public class Car extends Vehicle {

  private String name;

  public Car() {}

  public Car(String car) {
    this.name = car;
  }

  @Override
  public int hashCode() {
    return getName() != null ? getName().hashCode() : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Car)) return false;

    Car car = (Car) o;

    return getName() != null ? getName().equals(car.getName()) : car.getName() == null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
