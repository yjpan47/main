package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String COMPANY_FIELD_ID = "#company";
    private static final String SECTION_FIELD_ID = "#section";
    private static final String RANK_FIELD_ID = "#rank";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nricLabel;
    private final Label companyLabel;
    private final Label sectionLabel;
    private final Label rankLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nricLabel = getChildNode(NRIC_FIELD_ID);
        companyLabel = getChildNode(COMPANY_FIELD_ID);
        sectionLabel = getChildNode(SECTION_FIELD_ID);
        rankLabel = getChildNode(RANK_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getNric() {
        return nricLabel.getText();
    }

    public String getCompany() {
        return companyLabel.getText();
    }

    public String getSection() {
        return sectionLabel.getText();
    }

    public String getRank() {
        return rankLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        return getNric().equals(person.getNric().value)
                && getCompany().equals(person.getCompany().value)
                && getSection().equals(person.getSection().value)
                && getRank().equals(person.getRank().value)
                && getName().equals(person.getName().fullName)
                && getPhone().equals(person.getPhone().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
