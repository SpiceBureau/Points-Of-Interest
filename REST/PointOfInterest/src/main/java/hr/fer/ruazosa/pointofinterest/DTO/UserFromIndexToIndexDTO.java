package hr.fer.ruazosa.pointofinterest.DTO;

import hr.fer.ruazosa.pointofinterest.entity.User;

public class UserFromIndexToIndexDTO {
    private User user;
    private Integer fromIndex;
    private Integer toIndex;

    public UserFromIndexToIndexDTO(User user, Integer fromIndex, Integer toIndex) {
        this.user = user;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public User getUser() {
        return user;
    }

    public Integer getFromIndex() {
        return fromIndex;
    }

    public Integer getToIndex() {
        return toIndex;
    }
}
