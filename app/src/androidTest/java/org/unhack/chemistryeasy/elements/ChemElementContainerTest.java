/*
 * Copyright ChemistryEasy Project (https://vk.com/chemistryeasyru)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.unhack.chemistryeasy.elements;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;



public class ChemElementContainerTest {
    private static final int NUMBER_OF_ELEMENTS = 118;
    private Context appContext;

    @Before
    public void setup() {
        appContext = getInstrumentation().getTargetContext().getApplicationContext();
    }

    @Test
    public void initFromDb() throws Exception {
        ChemElementContainer mContainer = new ChemElementContainer(getContext());
        assertFalse(mContainer.initFromDb(null));
        assertTrue(mContainer.initFromDb(appContext));
        assertEquals("H", mContainer.getElementByNumber(1).getElementSymbol());
    }

    @Test
    public void getElementByNumber() throws Exception {
        ChemElementContainer mContainer = new ChemElementContainer(getContext());
        mContainer.initFromDb(appContext);
        assertEquals("H", mContainer.getElementByNumber(1).getElementSymbol());
        assertEquals("He", mContainer.getElementByNumber(2).getElementSymbol());
        assertEquals("Og", mContainer.getElementByNumber(118).getElementSymbol());
        assertNull(mContainer.getElementByNumber(119));
        assertNull(mContainer.getElementByNumber(0));
        assertNull(mContainer.getElementByNumber(-6547354));
    }

    @Test
    public void getFilteredElements() throws Exception {
        int[] filter = {1,2};
        ChemElementContainer mContainer = new ChemElementContainer(getContext());
        mContainer.initFromDb(appContext);
        assertEquals(2, mContainer.getFilteredElements(filter).size());
        int[] falseFilter = {1,2,3,234,546,567,435,-1};
        assertEquals(3, mContainer.getFilteredElements(falseFilter).size());
        assertEquals("He", mContainer.getFilteredElements(filter).get(2).getElementSymbol());
    }

    @Test
    public void getAll() throws Exception {
        ChemElementContainer mContainer = new ChemElementContainer(getContext());
        mContainer.initFromDb(appContext);
        HashMap<Integer, ChemElement> elems = mContainer.getAll();
        assertEquals(elems.size(), NUMBER_OF_ELEMENTS);
    }

    @Test
    public void putElement() throws Exception {
        ChemElementContainer mContainer = new ChemElementContainer(getContext());
        mContainer.putElement(null);
        assertEquals(0, mContainer.getAll().size());
        ChemElement mCustomElem = new ChemElement(getContext(),120, "symbol", "name", (float)1.03, false);
        mContainer.putElement(mCustomElem);
    }

}