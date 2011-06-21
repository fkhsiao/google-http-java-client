/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Tests {@link ClassInfo}.
 *
 * @author Yaniv Inbar
 */
public class ClassInfoTest extends TestCase {

  public enum E {

    @Value
    VALUE,
    @Value("other")
    OTHER_VALUE,
    @NullValue
    NULL, IGNORED_VALUE
  }

  public void testIsEnum() {
    assertTrue(ClassInfo.of(E.class).isEnum());
    assertFalse(ClassInfo.of(String.class).isEnum());
  }

  public void testGetFieldInfo_enum() throws Exception {
    ClassInfo classInfo = ClassInfo.of(E.class);
    assertEquals(E.class.getField("NULL"), classInfo.getFieldInfo(null).getField());
    assertEquals(E.class.getField("OTHER_VALUE"), classInfo.getFieldInfo("other").getField());
    assertEquals(E.class.getField("VALUE"), classInfo.getFieldInfo("VALUE").getField());
  }

  public class A {
    @Key
    String b;
    @Key("oc")
    String c;
    String d;
  }

  public class B extends A {
    @Key
    String e;
  }

  public void testNames() {
    assertEquals(ImmutableList.of("b", "oc"), ClassInfo.of(A.class).names);
    assertEquals(ImmutableList.of("b", "e", "oc"), ClassInfo.of(B.class).names);
  }

  public void testNames_enum() {
    ClassInfo classInfo = ClassInfo.of(E.class);
    assertEquals(Lists.newArrayList(Arrays.asList(null, "VALUE", "other")), classInfo.names);
  }
}