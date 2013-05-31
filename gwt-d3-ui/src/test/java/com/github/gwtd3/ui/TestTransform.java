package com.github.gwtd3.ui;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.gwtd3.api.core.Selection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Selection.class })
public class TestTransform {

    @Test
    public void testTransform() {
        Transform t = Transform.parse("");
        assertThat(t.isEmpty()).isTrue();
        //
        t.pushTranslate(5, 4);
        assertThat(t.buildAttribute()).isEqualTo("translate(5,4)");
        //
        t.pushTranslate(10);
        assertThat(t.buildAttribute()).isEqualTo("translate(5,4),translate(10)");

    }
}
