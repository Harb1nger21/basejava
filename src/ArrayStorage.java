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
        Resume resume = new Resume();
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume.uuid = uuid;
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                countElements--;
            }
        }
        for (int i = 0; i < countElements + 1; i++) {
            if (storage[i] == null) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[countElements];
        int indexReturn = 0;
        for (int i = 0; i < countElements; i++) {
            allResumes[indexReturn] = storage[i];
            indexReturn++;
        }
        return allResumes;
    }

    int size() {
        return countElements;
    }
}
