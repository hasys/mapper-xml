/*
 * Copyright © 2021 Treblereel
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

package org.treblereel.gwt.xml.mapper.client.tests.annotations.xmlelementref.interfaces;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import org.treblereel.gwt.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
@XmlRootElement(name = "tasks")
public class TasksContainer {

  @XmlElement(name = "Task1", type = Task.class)
  private ITask task1;

  @XmlElementRef(name = "Task2", type = Task.class)
  private ITask task2;

  public ITask getTask1() {
    return task1;
  }

  public void setTask1(ITask task1) {
    this.task1 = task1;
  }

  public ITask getTask2() {
    return task2;
  }

  public void setTask2(ITask task2) {
    this.task2 = task2;
  }
}
