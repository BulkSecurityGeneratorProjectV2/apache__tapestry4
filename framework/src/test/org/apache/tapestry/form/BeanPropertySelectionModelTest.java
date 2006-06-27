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
package org.apache.tapestry.form;

import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.BaseComponentTestCase;
import org.testng.annotations.Test;


/**
 * Tests the functionality of {@link BeanPropertySelectionModel}.
 *
 * @author jkuhnert
 */
@Test
public class BeanPropertySelectionModelTest extends BaseComponentTestCase
{
    
    /**
     * Tests using a null arg constuctor.
     */
    public void testNullModel()
    {
        BeanPropertySelectionModel model = new BeanPropertySelectionModel();
        assertEquals(model.getOptionCount(), 0);
    }
    
    /**
     * Uses {@link BeanPropertySelectionModelTest} as the 
     * model.
     */
    public void testBasicModel()
    {
        List<SimpleBean> list = new ArrayList();
        list.add(new SimpleBean(1, "Name 1", "Description 1"));
        list.add(new SimpleBean(2, "Name 2", "Description 2"));
        list.add(new SimpleBean(3, "Name 3", "Description 3"));
        
        BeanPropertySelectionModel model =
            new BeanPropertySelectionModel(list, "name");
        
        assertEquals(model.getOptionCount(), 3);
        
        SimpleBean b2 = (SimpleBean)model.getOption(1);
        assertEquals(b2.getId(), 2);
        assertEquals(model.getLabel(2), "Name 3");
        
        assertEquals(model.translateValue("1"), b2);
    }
}
