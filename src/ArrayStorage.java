/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countElements = 0;

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
        //Resume resume = new Resume();
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                //resume.uuid = uuid;
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = 1; j < countElements; j++) {
                    storage[i] = storage[j];
                }
                countElements--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[countElements];
        for (int i = 0; i < countElements; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    int size() {
        return countElements;
    }
}
