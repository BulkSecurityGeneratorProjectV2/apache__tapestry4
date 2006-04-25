// Copyright 2004, 2005 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry.util.prop;

import java.util.Map;

import ognl.ClassResolver;

/**
 * Implementation of OGNL's ClassResolver (which is unfortunately, named
 * the same as HiveMind's ClassResolver).
 *
 * @author Howard Lewis Ship
 */
public class OgnlClassResolver implements ClassResolver
{
    private ClassLoader _loader;

    /**
     * Creates a new ClassResolver with the current thread's context class loader
     *
     */
    public OgnlClassResolver()
    {
        this(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Creates a new ClassResolver with the specified class loader
     * @param loader ClassLoader to use for class resolution
     */
    public OgnlClassResolver(ClassLoader loader)
    {
        _loader = loader;
    }

    /**
     * 
     * @see ognl.ClassResolver#classForName(java.lang.String, java.util.Map)
     */
    public Class classForName(String name, Map context) throws ClassNotFoundException
    {
        return Class.forName(name, true, _loader);
    }

}
