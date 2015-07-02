/*
 * Copyright 2013 Google Inc.
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

package com.wab.lernapp;

import com.wab.lernapp.wizard.model.AbstractWizardModel;
import com.wab.lernapp.wizard.model.PageList;
import com.wab.lernapp.wizard.model.SingleFixedChoicePage;

import android.content.Context;

public class TestModel extends AbstractWizardModel {
    public TestModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                //auditiver Lerntyp
                                new SingleFixedChoicePage(this, "Geschichten höre ich sehr gern.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Wenn ich eine Melodie höre, kann ich mich daran erinnern.")
                                    .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                    .setRequired(true),

                                new SingleFixedChoicePage(this, "Geschichten, die ich gehört habe, kann ich gut nacherzählen.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Vortragenden kann ich auch bei schweren Sachverhalten folgen.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Liedtexte behalte ich schnell im Kopf.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich kann gut zuhören und die Aufforderungen umsetzen.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

        //Visueller Typ
                                new SingleFixedChoicePage(this, "Tabellen und Bilder behalte ich schnell im Kopf.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich spiele gerne und gut Memory oder Puzzle.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich lese gern und kann die Geschichten anschließend gut wiedergeben.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich merke mir viele Details der Kleidung und des Aussehens meines Gegenübers.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich orientiere mich bei Spaziergängen an der Umgebung.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Ich träume oft farbig mit vielen Details.")
                                        .setChoices("Trifft voll und ganz zu", "Trifft zu", "Trifft nicht zu", "Trifft überhaupt nicht zu")
                                        .setRequired(true)

        );
    }
}
