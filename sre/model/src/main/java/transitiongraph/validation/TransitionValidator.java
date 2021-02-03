/**
 *
 * $Id$
 */
package transitiongraph.validation;

import transitiongraph.State;

/**
 * A sample validator interface for {@link transitiongraph.Transition}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TransitionValidator {
    boolean validate();

    boolean validateLabel(String value);
    boolean validateSource(State value);
    boolean validateTarget(State value);
    boolean validateProbability(double value);
}