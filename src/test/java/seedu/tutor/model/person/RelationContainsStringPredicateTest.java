package seedu.tutor.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.tutor.testutil.PersonBuilder;

public class RelationContainsStringPredicateTest {

    @Test
    public void equals() {

        String firstPredicateKeyword = "test";
        String secondPredicateKeyword = "test2";

        RelationContainsStringPredicate firstPredicate = new RelationContainsStringPredicate(firstPredicateKeyword);
        RelationContainsStringPredicate secondPredicate = new RelationContainsStringPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        RelationContainsStringPredicate firstPredicateCopy = new RelationContainsStringPredicate(firstPredicateKeyword);
        // same values -> returns true
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different object -> returns false
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_relationContainsKeyword_returnsTrue() {
        Person person = new PersonBuilder().withRelations("Bob/Alice/brother/sister").build();
        RelationContainsStringPredicate predicate = new RelationContainsStringPredicate("brother");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("sister");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("sis");
        assertTrue(predicate.test(person));

    }

    @Test
    public void test_relationContainsKeywordButInDifferentCase_returnsTrue() {
        Person person = new PersonBuilder().withRelations("Bob/Alice/brother/sister").build();
        RelationContainsStringPredicate predicate = new RelationContainsStringPredicate("Bob");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("alice");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("Brother");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("SiStEr");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_keywordContainsPartialSubstring_returnsTrue() {
        Person person = new PersonBuilder().withRelations("Bob/Alice/brother/sister").build();
        RelationContainsStringPredicate predicate = new RelationContainsStringPredicate("ob");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("ali");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("ther");
        assertTrue(predicate.test(person));

        predicate = new RelationContainsStringPredicate("sis");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_relationDoesNotContainKeyword_returnsFalse() {
        Person person = new PersonBuilder().withRelations("Bob/Alice/brother/sister").build();
        RelationContainsStringPredicate predicate = new RelationContainsStringPredicate("test");
        assertFalse(predicate.test(person));

        predicate = new RelationContainsStringPredicate("lalalala");
        assertFalse(predicate.test(person));
    }

}
