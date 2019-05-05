/**
 * Copyright 2004 - 2017 Syncleus, Inc.
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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VertexLabelTest {
    
    private FramedGraph fg;
    
    @Before
    public void setUp() {
        fg = new DelegatingFramedGraph(TinkerGraph.open(), true, true);
    }
    
    @After
    public void tearDown() throws IOException {
        fg.close();
    }
    
    @Test
    public void testNotLabeled() {
        Friend friend1 = fg.addFramedVertex(Friend.DEFAULT_INITIALIZER);
        Friend friend2 = fg.addFramedVertex(Friend.class);
        Assert.assertEquals(Vertex.DEFAULT_LABEL, friend1.getLabel());
        Assert.assertEquals(Vertex.DEFAULT_LABEL, friend2.getLabel());
        Assert.assertEquals(2, fg.traverse((g) -> g.V().hasLabel(Vertex.DEFAULT_LABEL)).toList(Friend.class).size());
        Assert.assertEquals(0, fg.traverse((g) -> g.V().hasLabel("friend")).toList(Friend.class).size());
        
    }
    
    @Test
    public void testLabeled() {
        FriendLabeled friend1 = fg.addFramedVertex(FriendLabeled.DEFAULT_INITIALIZER);
        FriendLabeled friend2 = fg.addFramedVertex(FriendLabeled.class);
        Assert.assertEquals("friend", friend1.getLabel());
        Assert.assertEquals("friend", friend2.getLabel());
        Assert.assertEquals(2, fg.traverse((g) -> g.V().hasLabel("friend")).toList(FriendLabeled.class).size());
        Assert.assertEquals(0, fg.traverse((g) -> g.V().hasLabel(Vertex.DEFAULT_LABEL)).toList(FriendLabeled.class).size());
    }
    
    @Test
    public void testKeyValueLabeled() {
        Friend friend1 = fg.addFramedVertex(Friend.DEFAULT_INITIALIZER, T.label, "friend");
        Friend friend2 = fg.addFramedVertex(Friend.class, T.label, "friend");
        Assert.assertEquals("friend", friend1.getLabel());
        Assert.assertEquals("friend", friend2.getLabel());
        Assert.assertEquals(2, fg.traverse((g) -> g.V().hasLabel("friend")).toList(Friend.class).size());
        Assert.assertEquals(0, fg.traverse((g) -> g.V().hasLabel(Vertex.DEFAULT_LABEL)).toList(Friend.class).size());
    }
    
}
