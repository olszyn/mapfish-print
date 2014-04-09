/*
 * Copyright (C) 2014  Camptocamp
 *
 * This file is part of MapFish Print
 *
 * MapFish Print is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapFish Print is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapFish Print.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mapfish.print.attribute.map;

import org.json.JSONObject;
import org.junit.Test;
import org.mapfish.print.AbstractMapfishSpringTest;
import org.mapfish.print.attribute.Attribute;
import org.mapfish.print.config.Configuration;
import org.mapfish.print.config.ConfigurationFactory;
import org.mapfish.print.config.Template;
import org.mapfish.print.json.PJsonObject;
import org.mapfish.print.processor.map.CreateMapProcessorFlexibleScaleBBoxGeoJsonTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.mapfish.print.processor.map.CreateMapProcessorFlexibleScaleBBoxGeoJsonTest.BASE_DIR;
import static org.mapfish.print.processor.map.CreateMapProcessorFlexibleScaleBBoxGeoJsonTest.loadJsonRequestData;

/**
 * @author Jesse on 4/3/14.
 */
public class MapAttributeTest extends AbstractMapfishSpringTest {

    @Autowired
    private ConfigurationFactory configurationFactory;

    @Test (expected = IllegalArgumentException.class)
    public void testMaxDpi() throws Exception {
        final File configFile = getFile(CreateMapProcessorFlexibleScaleBBoxGeoJsonTest.class, BASE_DIR + "config.yaml");
        final Configuration config = configurationFactory.getConfig(configFile);
        final Template template = config.getTemplate("main");

        final PJsonObject pJsonObject = loadJsonRequestData();
        final PJsonObject attributesJson = pJsonObject.getJSONObject("attributes");
        final JSONObject mapDef = attributesJson.getJSONObject("mapDef").getInternalObj();
        mapDef.remove("dpi");
        mapDef.accumulate("dpi", 1000);

        final Attribute<?> mapAttribute = template.getAttributes().get("mapDef");
        mapAttribute.getValue(template, attributesJson, "mapDef");
    }
}