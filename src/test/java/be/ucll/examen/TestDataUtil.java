package be.ucll.examen;

import be.ucll.examen.domain.dto.CampusDto;
import be.ucll.examen.domain.entities.CampusEntity;
import be.ucll.examen.domain.entities.RoomEntity;

import java.util.ArrayList;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static CampusEntity createTestCampusA() {
        CampusEntity campus = new CampusEntity();
        campus.setName("TestNameA");
        campus.setAddress("TestAdressA");
        campus.setParkingCapacity(200);
        campus.setRooms(new ArrayList<>());
        campus.setNumberOfRooms(0);
        return campus;
    }

    public static CampusDto createTestCampusDtoA() {
        CampusDto dto = new CampusDto();
        dto.setName("TestNameA");
        dto.setAddress("TestAdressA");
        dto.setParkingCapacity(200);
        dto.setRooms(new ArrayList<>());
        dto.setNumberOfRooms(0);
        return dto;
    }

    public static CampusEntity createTestCampusB() {
        CampusEntity campus = new CampusEntity();
        campus.setName("TestNameB");
        campus.setAddress("TestAdressB");
        campus.setParkingCapacity(100);
        return campus;
    }

    public static CampusEntity createTestCampusC() {
        CampusEntity campus = new CampusEntity();
        campus.setName("TestNameC");
        campus.setAddress("TestAdressC");
        campus.setParkingCapacity(50);
        return campus;
    }

    public static RoomEntity createTestRoomA(CampusEntity campus) {
        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setName("TestNaamA");
        room.setType("TestTypeA");
        room.setCapacity(50);
        room.setFloorNumber(2);
        room.setCampus(campus);
        return room;
    }

    public static RoomEntity createTestRoomB(CampusEntity campus) {
        RoomEntity room = new RoomEntity();
        room.setId(2L);
        room.setName("TestNaamB");
        room.setType("TestTypeB");
        room.setCapacity(20);
        room.setFloorNumber(1);
        room.setCampus(campus);
        return room;
    }

    public static RoomEntity createTestRoomC(CampusEntity campus) {
        RoomEntity room = new RoomEntity();
        room.setId(3L);
        room.setName("TestNaamC");
        room.setType("TestTypeC");
        room.setCapacity(30);
        room.setFloorNumber(0);
        room.setCampus(campus);
        return room;
    }
}
