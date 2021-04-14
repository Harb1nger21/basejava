/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    static int countElements = 0;

    void clear() {
        for (int i = 0; i < countElements; i++) {
            storage[i] = null;
        }
        countElements = 0;
    }

    void save(Resume r) {
        storage[countElements] = r;
        countElements++;
    }

    Resume get(String uuid) {
        Resume forReturn = new Resume();
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                forReturn.uuid = uuid;
                break;
            }
        }
        return forReturn;
    }

    void delete(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
            }
        }
        countElements--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] forReturn = new Resume[countElements];
        int indexReturn = 0;
        for (int i = 0; i <= countElements; i++) {
            if (storage[i] != null) {
                forReturn[indexReturn] = storage[i];
                indexReturn++;
            }
        }
        return forReturn;
    }

    int size() {
        return countElements;
    }
}
