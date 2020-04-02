
public class Table extends BaseEntity<BigInteger> {

    private int capacity;

    public Table(@JsonProperty("name") String name, @JsonProperty("id") BigInteger id, @JsonProperty("capacity") int capacity) {
        super(id, name);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, capacity: %s}", this.getId(), this.getName(), this.getCapacity());
    }
}