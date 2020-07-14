package com.fafafashop.lyrics;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.fafafashop.lyrics");

        noClasses()
            .that()
                .resideInAnyPackage("com.fafafashop.lyrics.service..")
            .or()
                .resideInAnyPackage("com.fafafashop.lyrics.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.fafafashop.lyrics.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
