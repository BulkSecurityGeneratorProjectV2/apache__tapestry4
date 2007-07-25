// Copyright 2005 The Apache Software Foundation
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

package org.apache.tapestry.annotations;

import java.lang.reflect.Method;

import org.apache.hivemind.Location;
import org.apache.tapestry.enhance.EnhancementOperation;
import org.apache.tapestry.spec.IComponentSpecification;
import org.apache.tapestry.spec.InjectSpecification;
import org.apache.tapestry.spec.InjectSpecificationImpl;
import org.apache.tapestry.engine.IPropertySource;

/**
 * 
 * @author Howard Lewis Ship
 */
public class InjectStateFlagAnnotationWorker implements MethodAnnotationEnhancementWorker
{

    public void performEnhancement(EnhancementOperation op, IComponentSpecification spec,
            Method method, Location location, IPropertySource propertySource)
    {
        InjectStateFlag isv = method.getAnnotation(InjectStateFlag.class);
        
        String keyName = isv.value();

        if (keyName.equals(""))
        {
            keyName = AnnotationUtils.convertMethodNameToKeyName(method.getName());
            if (keyName.endsWith("-exists")) // strip it
            {
                int length = keyName.length();
                keyName = keyName.substring(0, length - 7);
            }
        }

        String propertyName = AnnotationUtils.getPropertyName(method);

        InjectSpecification inject = new InjectSpecificationImpl();

        inject.setType("state-flag");
        inject.setProperty(propertyName);
        inject.setObject(keyName);
        inject.setLocation(location);

        spec.addInjectSpecification(inject);
    }

}
