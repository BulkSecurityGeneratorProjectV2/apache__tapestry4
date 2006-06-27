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

package org.apache.tapestry.enhance;

import static org.easymock.EasyMock.expect;

import java.lang.reflect.Modifier;

import org.apache.hivemind.Location;
import org.apache.hivemind.Resource;
import org.apache.hivemind.service.MethodSignature;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IScript;
import org.apache.tapestry.engine.IScriptSource;
import org.apache.tapestry.spec.InjectSpecificationImpl;
import org.testng.annotations.Test;

/**
 * Tests for {@link org.apache.tapestry.enhance.InjectScriptWorker}.
 * 
 * @author Howard M. Lewis Ship
 * @since 4.0
 */
@Test
public class TestInjectScriptWorker extends BaseComponentTestCase
{
    public void testSuccess()
    {
        EnhancementOperation op = newMock(EnhancementOperation.class);

        Location componentSpecLocation = newLocation();
        final Resource scriptResource = componentSpecLocation.getResource().getRelativeResource(
                "bar.script");

        final Location injectSpecLocation = newLocation();

        final IScriptSource source = newMock(IScriptSource.class);

        op.claimReadonlyProperty("foo");

        expect(op.getPropertyType("foo")).andReturn(IScript.class);

        expect(op.getAccessorMethodName("foo")).andReturn("getFoo");

        expect(op.addInjectedField("_$script", DeferredScript.class, new DeferredScriptImpl(scriptResource,
                source, injectSpecLocation)))
                .andReturn("_script");
        /* opc.setMatcher(new AggregateArgumentsMatcher(new ArgumentMatcher[]
        { null, null, new ArgumentMatcher()
        {

            public boolean compareArguments(Object expected, Object actual)
            {
                DeferredScriptImpl ds = (DeferredScriptImpl) actual;

                return ds._location == injectSpecLocation && ds._scriptSource == source
                        && ds._scriptResource.equals(scriptResource);
            }

        }

        }));*/

        MethodSignature sig = new MethodSignature(IScript.class, "getFoo", null, null);

        op.addMethod(Modifier.PUBLIC, sig, "return _script.getScript();", injectSpecLocation);

        replay();

        InjectSpecificationImpl is = new InjectSpecificationImpl();
        is.setProperty("foo");
        is.setObject("bar.script");
        is.setLocation(injectSpecLocation);

        InjectScriptWorker worker = new InjectScriptWorker();
        worker.setSource(source);

        worker.performEnhancement(op, is);

        verify();
    }
}
