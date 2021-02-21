package ba.unsa.etf.rpr.project;

public class PublicationType extends ChoiceField{
    @Override
    public String getTitle() {
        return "Publication type: " + super.getTitle();
    }
}
