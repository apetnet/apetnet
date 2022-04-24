package ru.apetnet.desktop.mock

import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.model.MockedMurataSolution
import ru.apetnet.desktop.model.MockedPetriNet
import ru.apetnet.desktop.model.MockedReachabilityTreeResult
import java.util.*

internal object PnMock {
    val netList: List<MockedPetriNet> = listOf(
        objectNetwork1(),
        objectNetwork2(),
        objectNetwork3(),

        objectNetwork4(),

        objectNetwork5(),
        objectNetwork6(),
        objectNetwork7(),
        objectNetwork8(),
        objectNetwork9(),
        objectNetwork10(),
        objectNetwork11(),
        objectNetwork12(),
        objectNetwork13(),
        objectNetwork14_1(),
        objectNetwork14_2()
    )

    /**
     * <img src="img\network1.png">
     */
    private fun objectNetwork1(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network1",
            matI = listOf(
                listOf(1, 0, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 0, 1, 1),
                listOf(0, 0, 0, 0),
            ),
            matO = listOf(
                listOf(0, 0, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 1, 1, 1),
            ),
            matC = listOf(
                listOf(-1, 0, 0, 0),
                listOf(1, -1, 0, 0),
                listOf(1, 0, -1, -1),
                listOf(0, 1, 1, 1)
            ),
            matMarking = listOf(
                listOf(1, 0, 1, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("33c3848d-06eb-46cd-bb49-bc1ec225af14"),
                    name = "P1",
                    tokensNumber = 1,
                    description = ""
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("ea6e03a1-daf5-4fde-8f19-91d96a56b09f"),
                    name = "P2",
                    tokensNumber = 0,
                    description = ""
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("1a1704db-4f20-4223-97bb-75c481fab8ac"),
                    name = "P3",
                    tokensNumber = 1,
                    description = ""
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("d89e97ae-f975-4f7a-ab93-a1e19edd5c23"),
                    name = "P4",
                    tokensNumber = 0,
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("e1296a81-9559-43e3-8a04-b619bf6608fd"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("3503e3e0-e2d3-4aae-8be7-52dcb879870f"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("a3280640-705d-4639-ab7e-2bb85baed466"),
                    name = "T3",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("6220c429-5279-45a4-bdd2-23f4552b5b9b"),
                    name = "T4",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("8fda40ac-a847-4af0-8b63-03d80b9c165f"),
                    name = "",
                    source = UUID.fromString("33c3848d-06eb-46cd-bb49-bc1ec225af14"),
                    receiver = UUID.fromString("e1296a81-9559-43e3-8a04-b619bf6608fd"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("26d0f1b8-041a-49a5-bdc7-7cc6a4066f27"),
                    name = "",
                    source = UUID.fromString("e1296a81-9559-43e3-8a04-b619bf6608fd"),
                    receiver = UUID.fromString("ea6e03a1-daf5-4fde-8f19-91d96a56b09f"),
                    description = ""

                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("fd717348-4c80-4e3b-a937-ad0437badec0"),
                    name = "",
                    source = UUID.fromString("e1296a81-9559-43e3-8a04-b619bf6608fd"),
                    receiver = UUID.fromString("1a1704db-4f20-4223-97bb-75c481fab8ac"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("ef0c3eea-b654-4ad2-ba7d-04ed35532351"),
                    name = "",
                    source = UUID.fromString("1a1704db-4f20-4223-97bb-75c481fab8ac"),
                    receiver = UUID.fromString("a3280640-705d-4639-ab7e-2bb85baed466"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("3a2aac42-0d48-46aa-ad78-29d416704cc5"),
                    name = "",
                    source = UUID.fromString("1a1704db-4f20-4223-97bb-75c481fab8ac"),
                    receiver = UUID.fromString("6220c429-5279-45a4-bdd2-23f4552b5b9b"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("b12f9292-a80e-4dc5-835b-dabdc47982d9"),
                    name = "",
                    source = UUID.fromString("a3280640-705d-4639-ab7e-2bb85baed466"),
                    receiver = UUID.fromString("d89e97ae-f975-4f7a-ab93-a1e19edd5c23"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("08214473-7d13-431b-8bca-428065122258"),
                    name = "",
                    source = UUID.fromString("6220c429-5279-45a4-bdd2-23f4552b5b9b"),
                    receiver = UUID.fromString("d89e97ae-f975-4f7a-ab93-a1e19edd5c23"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("ef2d04db-46a6-43eb-80c9-42e5635f2686"),
                    name = "",
                    source = UUID.fromString("ea6e03a1-daf5-4fde-8f19-91d96a56b09f"),
                    receiver = UUID.fromString("3503e3e0-e2d3-4aae-8be7-52dcb879870f"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("5f9030e1-3796-4ac0-9d60-002a63b05444"),
                    name = "",
                    source = UUID.fromString("3503e3e0-e2d3-4aae-8be7-52dcb879870f"),
                    receiver = UUID.fromString("d89e97ae-f975-4f7a-ab93-a1e19edd5c23"),
                    description = ""
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 1, 0), // M1
                    listOf(1, 0, 0, 1), // M2
                    listOf(0, 1, 1, 1), // M3
                    listOf(0, 0, 1, 2), // M4
                    listOf(0, 1, 0, 2), // M5
                    listOf(0, 1, 2, 0), // M6
                    listOf(0, 0, 2, 1), // M7
                    listOf(0, 0, 0, 3)  // M8
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"), // 1
                    listOf("M2", "T2", "M3"), // 2
                    listOf("M3", "T3", "M4"), // 3
                    listOf("M3", "T4", "M4"), // 4
                    listOf("M4", "T3", "M5"), // 5
                    listOf("M4", "T4", "M5"), // 6
                    listOf("M1", "T3", "M8"), // 7
                    listOf("M1", "T4", "M8"), // 8
                    listOf("M8", "T1", "M6"), // 9
                    listOf("M6", "T2", "M4"), // 10
                    listOf("M6", "T3", "M7"), // 11
                    listOf("M6", "T4", "M7"), // 12
                    listOf("M7", "T2", "M5"),  // 13
                    listOf("M2", "T3", "M6"),  // 14
                    listOf("M2", "T4", "M6"),  // 15
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2", "x3", "x4"),
                colNamesY = listOf("y1", "y2", "y3", "y4"),
                solutionX = listOf(0, 0, 0, 0),
                solutionY = listOf(2, 1, 1, 1),
                isConsistent = false,
                isConservative = true
            )
        )
    }


    /**
     * <img src="img\network2.png">
     */
    private fun objectNetwork2(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network2",
            matI = listOf(
                listOf(1),
                listOf(0),
            ),
            matO = listOf(
                listOf(1),
                listOf(1)
            ),
            matC = listOf(
                listOf(0),
                listOf(1)
            ),
            matMarking = listOf(
                listOf(1, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("42e129aa-6c39-4829-b27c-734ab78e7bac"),
                    name = "P1",
                    tokensNumber = 1,
                    description = ""
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("2d4452d4-40c2-4750-a8d0-d59a23a41b52"),
                    name = "P2",
                    tokensNumber = 0,
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("2f8dbfa7-c60e-4423-8b16-2c1504cf91c4"),
                    name = "T1",
                    description = ""
                ),

                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("7ad5a5fe-ed91-4f01-8ed6-829043f8943c"),
                    name = "",
                    source = UUID.fromString("42e129aa-6c39-4829-b27c-734ab78e7bac"),
                    receiver = UUID.fromString("2f8dbfa7-c60e-4423-8b16-2c1504cf91c4"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("fa9ffcad-1734-4607-a94b-fc8b151b9417"),
                    name = "",
                    source = UUID.fromString("2f8dbfa7-c60e-4423-8b16-2c1504cf91c4"),
                    receiver = UUID.fromString("2d4452d4-40c2-4750-a8d0-d59a23a41b52"),
                    description = ""

                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("fa9ffcad-1734-4607-a94b-fc8b151b9417"),
                    name = "",
                    source = UUID.fromString("2f8dbfa7-c60e-4423-8b16-2c1504cf91c4"),
                    receiver = UUID.fromString("42e129aa-6c39-4829-b27c-734ab78e7bac"),
                    description = ""
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0), // 1
                    listOf(1, 1), // 2
                    listOf(1, -1), // 3
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T1", "M3"),
                    listOf("M3", "T1", "M3"),
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2"),
                solutionX = listOf(0),
                solutionY = listOf(1, 0),
                isConsistent = false,
                isConservative = false
            ),
            hasOmega = true
        )
    }

    /**
     * <img src="img\network3.png">
     */

    private fun objectNetwork3(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network3",
            matI = listOf(
                listOf(1),
                listOf(0),
            ),
            matO = listOf(
                listOf(0),
                listOf(1)
            ),
            matC = listOf(
                listOf(-1),
                listOf(1),
            ),
            matMarking = listOf(
                listOf(1, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("0d1c48e1-bdc8-4fde-bb53-e337464aa28a"),
                    name = "P1",
                    tokensNumber = 1,
                    description = ""
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("93e66231-4674-4a90-bf0e-369186cf6e6c"),
                    name = "P2",
                    tokensNumber = 0,
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("e0c76656-4a41-42a9-970e-caccb4aa1637"),
                    name = "T1",
                    description = ""
                ),

                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("46dd9804-a099-4765-9c67-c6318cedd6d8"),
                    name = "",
                    source = UUID.fromString("0d1c48e1-bdc8-4fde-bb53-e337464aa28a"),
                    receiver = UUID.fromString("e0c76656-4a41-42a9-970e-caccb4aa1637"),
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("fa9ffcad-1734-4607-a94b-fc8b151b9417"),
                    name = "",
                    source = UUID.fromString("e0c76656-4a41-42a9-970e-caccb4aa1637"),
                    receiver = UUID.fromString("93e66231-4674-4a90-bf0e-369186cf6e6c"),
                    description = ""
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0), // 1
                    listOf(0, 1), // 2
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2"),
                solutionX = listOf(0),
                solutionY = listOf(1, 1),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network4.png">
     */
    private fun objectNetwork4(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network4",
            matI = listOf(
                listOf(1, 0, 0, 0),
                listOf(0, 0, 0, 1),
                listOf(0, 1, 0, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 0, 0),
                listOf(0, 0, 0, 1),
                listOf(0, 0, 0, 0)
            ),
            matO = listOf(
                listOf(0, 0, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 0, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 0, 1)
            ),
            matC = listOf(
                listOf(-1, 0, 0, 0),
                listOf(1, 0, 0, -1),
                listOf(0, -1, 0, 0),
                listOf(0, 1, -1, 0),
                listOf(1, 0, -1, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, -1),
                listOf(0, 0, 0, 1)
            ),

            matMarking = listOf(
                listOf(1, 0, 1, 0, 0, 0, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("ce53713c-a845-45eb-938d-9882b2ec0fec"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("e967ed38-a165-4e7b-a3d0-5ff0eef3df4e"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("e49bb3d8-7fe3-497c-8c5c-0c15b38d366b"),
                    name = "P3",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("53400383-8f66-476a-a663-ddcfe7ada738"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("08d5cbc4-381f-4cc0-a205-8890e7298a23"),
                    name = "P5",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("9553ebb5-cd9b-435c-9740-db0d5296f33e"),
                    name = "P6",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("ecbaa634-0383-4c9e-b28f-0150ae6068f0"),
                    name = "P7",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("52f90799-446a-4192-b210-0c334add4d65"),
                    name = "P8",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("f61896d4-a361-4527-a6dc-a19ba7a8af0a"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("5d912b9c-15eb-42f7-90be-09529d2d6429"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("c4768c57-d2cb-4e4d-9655-bb2dd06d79bb"),
                    name = "T3",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("f8bdb2bb-5160-4854-89f6-9e18decf41b0"),
                    name = "T4",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("eec4c264-ab1b-4b13-96e5-b0044466b8c6"),
                    name = "",
                    description = "",
                    source = UUID.fromString("ce53713c-a845-45eb-938d-9882b2ec0fec"),
                    receiver = UUID.fromString("f61896d4-a361-4527-a6dc-a19ba7a8af0a")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("7ea94316-b5bb-4850-8368-bbe30299f8ae"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f61896d4-a361-4527-a6dc-a19ba7a8af0a"),
                    receiver = UUID.fromString("e967ed38-a165-4e7b-a3d0-5ff0eef3df4e")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("3d04060d-3c8d-4bcf-9b15-5b01d251802d"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f61896d4-a361-4527-a6dc-a19ba7a8af0a"),
                    receiver = UUID.fromString("08d5cbc4-381f-4cc0-a205-8890e7298a23")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("9c2a6170-b32e-430b-9ccb-81dad57df63b"),
                    name = "",
                    description = "",
                    source = UUID.fromString("e49bb3d8-7fe3-497c-8c5c-0c15b38d366b"),
                    receiver = UUID.fromString("5d912b9c-15eb-42f7-90be-09529d2d6429")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("9cb4edbc-4f72-4290-9d27-5ecb12cafff5"),
                    name = "",
                    description = "",
                    source = UUID.fromString("5d912b9c-15eb-42f7-90be-09529d2d6429"),
                    receiver = UUID.fromString("53400383-8f66-476a-a663-ddcfe7ada738")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("5fdb8997-4074-48bb-82fd-35934778ef12"),
                    name = "",
                    description = "",
                    source = UUID.fromString("08d5cbc4-381f-4cc0-a205-8890e7298a23"),
                    receiver = UUID.fromString("c4768c57-d2cb-4e4d-9655-bb2dd06d79bb")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("4518dc22-ff86-45b9-82ce-2a488caae95a"),
                    name = "",
                    description = "",
                    source = UUID.fromString("53400383-8f66-476a-a663-ddcfe7ada738"),
                    receiver = UUID.fromString("c4768c57-d2cb-4e4d-9655-bb2dd06d79bb")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("fe58beef-c7f6-468b-af94-3a4135c324c2"),
                    name = "",
                    description = "",
                    source = UUID.fromString("c4768c57-d2cb-4e4d-9655-bb2dd06d79bb"),
                    receiver = UUID.fromString("9553ebb5-cd9b-435c-9740-db0d5296f33e")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("b2393221-504f-4959-82dd-8e68b664b7e1"),
                    name = "",
                    description = "",
                    source = UUID.fromString("e967ed38-a165-4e7b-a3d0-5ff0eef3df4e"),
                    receiver = UUID.fromString("f8bdb2bb-5160-4854-89f6-9e18decf41b0")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("93a04689-b9f3-4cac-b7a9-a7608cff37c6"),
                    name = "",
                    description = "",
                    source = UUID.fromString("ecbaa634-0383-4c9e-b28f-0150ae6068f0"),
                    receiver = UUID.fromString("f8bdb2bb-5160-4854-89f6-9e18decf41b0")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("c2c8ebdd-e186-4875-b0a9-e2ebde972943"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f8bdb2bb-5160-4854-89f6-9e18decf41b0"),
                    receiver = UUID.fromString("52f90799-446a-4192-b210-0c334add4d65")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("24d44e39-a981-4a2a-943e-e0462d3597f6"),
                    name = "",
                    description = "",
                    source = UUID.fromString("c4768c57-d2cb-4e4d-9655-bb2dd06d79bb"),
                    receiver = UUID.fromString("ecbaa634-0383-4c9e-b28f-0150ae6068f0")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 1, 0, 0, 0, 0, 0), // 1
                    listOf(0, 1, 1, 0, 1, 0, 0, 0), // 2
                    listOf(0, 1, 0, 1, 1, 0, 0, 0), // 3
                    listOf(0, 1, 0, 0, 0, 1, 1, 0), // 4
                    listOf(0, 0, 0, 0, 0, 1, 0, 1), // 5
                    listOf(1, 0, 0, 1, 0, 0, 0, 0), // 6
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"), // 1
                    listOf("M1", "T2", "M6"), // 2
                    listOf("M2", "T2", "M3"), // 3
                    listOf("M6", "T1", "M3"), // 4
                    listOf("M3", "T3", "M4"), // 5
                    listOf("M4", "T4", "M5"), // 6
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2", "x3", "x4"),
                colNamesY = listOf("y1", "y2", "y3", "y4", "y5", "y6", "y7", "y8"),
                solutionX = listOf(0, 0, 0, 0),
                solutionY = listOf(7, 3, 6, 6, 4, 6, 4, 7),
                isConsistent = false,
                isConservative = true
            )
        )
    }


    /**
     * <img src="img\network5.png">
     */
    private fun objectNetwork5(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network5",
            matI = listOf(
                listOf(1, 0),
                listOf(0, 1),
                listOf(0, 1),
                listOf(0, 0)
            ),
            matO = listOf(
                listOf(0, 0),
                listOf(1, 0),
                listOf(0, 0),
                listOf(0, 1)
            ),
            matC = listOf(
                listOf(-1, 0),
                listOf(1, -1),
                listOf(0, -1),
                listOf(0, 1)
            ),
            matMarking = listOf(
                listOf(1, 0, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("d9f38173-855d-426b-a802-d96e2b5e8006"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("1ecc13d4-5b51-4736-8066-9d59e98281e0"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("c3c97903-e500-47e5-8187-5a39aa749fb5"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("413591d9-4800-48f7-8781-5ca14955a610"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("ba0c3c4f-996f-4fb7-be26-f0afda739be4"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("19c1498a-ba96-4a88-bfcb-9691c3cb4f26"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("8731b939-6bad-4690-8b57-2e383503aeec"),
                    name = "",
                    description = "",
                    source = UUID.fromString("d9f38173-855d-426b-a802-d96e2b5e8006"),
                    receiver = UUID.fromString("ba0c3c4f-996f-4fb7-be26-f0afda739be4")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e9bd1d45-e677-4310-94d8-80400540b440"),
                    name = "",
                    description = "",
                    source = UUID.fromString("ba0c3c4f-996f-4fb7-be26-f0afda739be4"),
                    receiver = UUID.fromString("1ecc13d4-5b51-4736-8066-9d59e98281e0")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("f15eaa7c-92dc-4bc8-9949-292ee8b86cb7"),
                    name = "",
                    description = "",
                    source = UUID.fromString("1ecc13d4-5b51-4736-8066-9d59e98281e0"),
                    receiver = UUID.fromString("19c1498a-ba96-4a88-bfcb-9691c3cb4f26")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("da95a101-a999-410b-8666-45828cc5baac"),
                    name = "",
                    description = "",
                    source = UUID.fromString("c3c97903-e500-47e5-8187-5a39aa749fb5"),
                    receiver = UUID.fromString("19c1498a-ba96-4a88-bfcb-9691c3cb4f26")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("a7d41d4f-209a-4c67-b8ab-a07a53cfba35"),
                    name = "",
                    description = "",
                    source = UUID.fromString("19c1498a-ba96-4a88-bfcb-9691c3cb4f26"),
                    receiver = UUID.fromString("413591d9-4800-48f7-8781-5ca14955a610")
                )

            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 0, 0), // 1
                    listOf(0, 1, 0, 0), // 2
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2"),
                colNamesY = listOf("y1", "y2", "y3", "y4"),
                solutionX = listOf(0, 0),
                solutionY = listOf(1, 1, 1, 2),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network6.png">
     */
    private fun objectNetwork6(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network6",
            matI = listOf(
                listOf(1, 0, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 0, 1),
                listOf(0, 0, 0, 1),
                listOf(0, 0, 0, 0)
            ),
            matO = listOf(
                listOf(0, 0, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 0, 1)
            ),
            matC = listOf(
                listOf(-1, 0, 0, 0),
                listOf(1, -1, 0, 0),
                listOf(1, 0, -1, 0),
                listOf(0, 1, 0, -1),
                listOf(0, 0, 1, -1),
                listOf(0, 0, 0, 1)
            ),
            matMarking = listOf(
                listOf(1, 0, 0, 0, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("ea29cd99-4560-4614-932d-7eae414fecfc"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("d9563f16-d572-4bb2-a1c4-d8655624b860"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("5652ce8e-91fe-4e25-843b-3352aa91ff47"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("66845d21-409b-4518-bafa-fce7b11a3434"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("37af333a-68be-4fa7-a3a6-9b36e9813d00"),
                    name = "P5",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("f2b5b342-880d-4288-bdb7-931552d9981d"),
                    name = "P6",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("b78fe7c8-d88b-45b5-9698-3c5395cf4a6c"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("072ae813-ace4-4c26-9ca7-81cbfe01193a"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("f71dfdf3-8d26-4d01-9227-8f021d9392a4"),
                    name = "T3",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("169d1f61-8a15-478b-8bcf-25700e0cc3dd"),
                    name = "T4",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("657849ac-06c0-4cf9-9c25-ea3a9de14000"),
                    name = "",
                    description = "",
                    source = UUID.fromString("ea29cd99-4560-4614-932d-7eae414fecfc"),
                    receiver = UUID.fromString("b78fe7c8-d88b-45b5-9698-3c5395cf4a6c")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("3ce2c5e0-49d7-4b7b-982c-c670ec5eea69"),
                    name = "",
                    description = "",
                    source = UUID.fromString("b78fe7c8-d88b-45b5-9698-3c5395cf4a6c"),
                    receiver = UUID.fromString("d9563f16-d572-4bb2-a1c4-d8655624b860")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("d9df2303-d53e-4f95-81a6-68afe03c95be"),
                    name = "",
                    description = "",
                    source = UUID.fromString("b78fe7c8-d88b-45b5-9698-3c5395cf4a6c"),
                    receiver = UUID.fromString("5652ce8e-91fe-4e25-843b-3352aa91ff47")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("d005ab82-c638-4c74-b922-75aa044d87bf"),
                    name = "",
                    description = "",
                    source = UUID.fromString("5652ce8e-91fe-4e25-843b-3352aa91ff47"),
                    receiver = UUID.fromString("f71dfdf3-8d26-4d01-9227-8f021d9392a4")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("ee6a3417-39d8-46b3-bd37-9fbbd1761674"),
                    name = "",
                    description = "",
                    source = UUID.fromString("d9563f16-d572-4bb2-a1c4-d8655624b860"),
                    receiver = UUID.fromString("072ae813-ace4-4c26-9ca7-81cbfe01193a")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("39733d48-ab43-434f-95b5-aeea3d2f9d77"),
                    name = "",
                    description = "",
                    source = UUID.fromString("072ae813-ace4-4c26-9ca7-81cbfe01193a"),
                    receiver = UUID.fromString("66845d21-409b-4518-bafa-fce7b11a3434")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("15e884b4-1bb6-4272-9b44-20165423dbb4"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f71dfdf3-8d26-4d01-9227-8f021d9392a4"),
                    receiver = UUID.fromString("37af333a-68be-4fa7-a3a6-9b36e9813d00")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("62f213ae-c93f-4df6-8558-73d9164c67df"),
                    name = "",
                    description = "",
                    source = UUID.fromString("37af333a-68be-4fa7-a3a6-9b36e9813d00"),
                    receiver = UUID.fromString("169d1f61-8a15-478b-8bcf-25700e0cc3dd")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("f9458caf-156a-41a0-9ce9-780607c07a73"),
                    name = "",
                    description = "",
                    source = UUID.fromString("66845d21-409b-4518-bafa-fce7b11a3434"),
                    receiver = UUID.fromString("169d1f61-8a15-478b-8bcf-25700e0cc3dd")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e4632630-480f-4be9-a5ee-6c9b2ca87552"),
                    name = "",
                    description = "",
                    source = UUID.fromString("169d1f61-8a15-478b-8bcf-25700e0cc3dd"),
                    receiver = UUID.fromString("f2b5b342-880d-4288-bdb7-931552d9981d")
                )

            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 0, 0, 0, 0), // 1
                    listOf(0, 1, 1, 0, 0, 0), // 2
                    listOf(0, 0, 1, 1, 0, 0), // 3
                    listOf(0, 0, 0, 0, 0, 1), // 4
                    listOf(0, 1, 0, 0, 1, 0), // 5
                    listOf(0, 0, 0, 1, 1, 0), // 6
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T2", "M3"),
                    listOf("M3", "T3", "M4"),
                    listOf("M4", "T4", "M5"),
                    listOf("M2", "T3", "M6"),
                    listOf("M6", "T2", "M4")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2", "x3", "x4"),
                colNamesY = listOf("y1", "y2", "y3", "y4", "y5", "y6"),
                solutionX = listOf(0, 0, 0, 0),
                solutionY = listOf(2, 1, 1, 1, 1, 2), //
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network7.png">
     */
    private fun objectNetwork7(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network7",
            matI = listOf(
                listOf(1, 1, 0),
                listOf(0, 0, 1),
                listOf(0, 0, 0),
                listOf(0, 0, 0),
                listOf(0, 0, 0)
            ),
            matO = listOf(
                listOf(0, 0, 0),
                listOf(1, 0, 0),
                listOf(0, 1, 0),
                listOf(0, 0, 1),
                listOf(0, 0, 1)
            ),
            matC = listOf(
                listOf(-1, -1, 0),
                listOf(1, 0, -1),
                listOf(0, 1, 0),
                listOf(0, 0, 1),
                listOf(0, 0, 1)
            ),
            matMarking = listOf(
                listOf(1, 0, 0, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("f3f157de-e0cd-4932-82cd-ecc3aedf58d5"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("f51ab251-3aa0-42e7-834a-f92cac890252"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("4dcafdff-b6a2-4c6c-8d44-7763964922f1"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("ab8d53b6-4dd9-47d8-bfd1-1521d27a0bfc"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("541e46d9-fad6-4ea9-bebc-3c9ba07db97e"),
                    name = "P5",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("739ca403-4c44-4f85-98f2-a81766929665"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("8ae0dda0-e315-4982-abc6-e925e69a394b"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("6ae56da7-a52b-4139-84d6-c77b499dc3b8"),
                    name = "T3",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("4ddb4e38-f8c0-45af-a17d-9e312dcc6803"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f3f157de-e0cd-4932-82cd-ecc3aedf58d5"),
                    receiver = UUID.fromString("739ca403-4c44-4f85-98f2-a81766929665")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("bdc7ec7b-6648-48a5-8b52-1b585ada3ead"),
                    name = "",
                    description = "",
                    source = UUID.fromString("739ca403-4c44-4f85-98f2-a81766929665"),
                    receiver = UUID.fromString("f51ab251-3aa0-42e7-834a-f92cac890252")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e39328fd-322d-4c17-b4ea-ebf8ed02ab80"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f51ab251-3aa0-42e7-834a-f92cac890252"),
                    receiver = UUID.fromString("6ae56da7-a52b-4139-84d6-c77b499dc3b8")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("c5a99835-aac2-408e-932c-16112a68f084"),
                    name = "",
                    description = "",
                    source = UUID.fromString("6ae56da7-a52b-4139-84d6-c77b499dc3b8"),
                    receiver = UUID.fromString("ab8d53b6-4dd9-47d8-bfd1-1521d27a0bfc")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("be67d541-1680-4307-8ef7-ba33ea708153"),
                    name = "",
                    description = "",
                    source = UUID.fromString("6ae56da7-a52b-4139-84d6-c77b499dc3b8"),
                    receiver = UUID.fromString("541e46d9-fad6-4ea9-bebc-3c9ba07db97e")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("028ae4af-7c61-4184-a437-e39bbbafb771"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f3f157de-e0cd-4932-82cd-ecc3aedf58d5"),
                    receiver = UUID.fromString("8ae0dda0-e315-4982-abc6-e925e69a394b")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e5b25989-9ca0-456d-a280-2efdbcd8c0c4"),
                    name = "",
                    description = "",
                    source = UUID.fromString("8ae0dda0-e315-4982-abc6-e925e69a394b"),
                    receiver = UUID.fromString("4dcafdff-b6a2-4c6c-8d44-7763964922f1")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 0, 0, 0), // 1
                    listOf(0, 1, 0, 0, 0), // 2
                    listOf(0, 0, 1, 0, 0), // 3
                    listOf(0, 0, 0, 1, 1), // 4
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T3", "M3"),
                    listOf("M1", "T2", "M4")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2", "x3"),
                colNamesY = listOf("y1", "y2", "y3", "y4", "y5"),
                solutionX = listOf(0, 0, 0),
                solutionY = listOf(4, 4, 4, 2, 2),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network8.png">
     */
    private fun objectNetwork8(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network8",
            matI = listOf(
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(0)
            ),
            matO = listOf(
                listOf(0),
                listOf(0),
                listOf(0),
                listOf(1)
            ),
            matC = listOf(
                listOf(-1),
                listOf(-1),
                listOf(-1),
                listOf(1)
            ),
            matMarking = listOf(
                listOf(1, 1, 1, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("824a6416-009d-4977-ad69-283d983005d9"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("b83f17ef-5c8a-4ed7-8fdf-dc3f9af78aab"),
                    name = "P2",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("054baf99-3de1-4b42-af7a-c395a34e948e"),
                    name = "P3",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("93f9c11c-e30b-4dc3-8eb9-79493d02cc8c"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("630b5eac-902f-49cb-85b1-a1e8378a638c"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("053a32b0-8574-4428-aece-72a555f42c8c"),
                    name = "",
                    description = "",
                    source = UUID.fromString("824a6416-009d-4977-ad69-283d983005d9"),
                    receiver = UUID.fromString("630b5eac-902f-49cb-85b1-a1e8378a638c")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("8fc07d4b-c6a7-41b6-b50a-9edd8cb69a41"),
                    name = "",
                    description = "",
                    source = UUID.fromString("b83f17ef-5c8a-4ed7-8fdf-dc3f9af78aab"),
                    receiver = UUID.fromString("630b5eac-902f-49cb-85b1-a1e8378a638c")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e25d8c92-4a1f-4b3e-b9b5-b584132f2063"),
                    name = "",
                    description = "",
                    source = UUID.fromString("054baf99-3de1-4b42-af7a-c395a34e948e"),
                    receiver = UUID.fromString("630b5eac-902f-49cb-85b1-a1e8378a638c")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("b832db01-315a-492e-bc9f-1ff36a85ee66"),
                    name = "",
                    description = "",
                    source = UUID.fromString("630b5eac-902f-49cb-85b1-a1e8378a638c"),
                    receiver = UUID.fromString("93f9c11c-e30b-4dc3-8eb9-79493d02cc8c")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 1, 1, 0), // 1
                    listOf(0, 0, 0, 1), // 2
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2", "y3", "y4"),
                solutionX = listOf(0),
                solutionY = listOf(1, 1, 1, 3),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network9.png">
     */
    private fun objectNetwork9(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network9",
            matI = listOf(
                listOf(1),
                listOf(0),
                listOf(0)
            ),
            matO = listOf(
                listOf(0),
                listOf(1),
                listOf(1)
            ),
            matC = listOf(
                listOf(-1),
                listOf(1),
                listOf(1)
            ),
            matMarking = listOf(
                listOf(1, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("2b3f6c2c-55f8-4f04-811e-a450bdaa45c1"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("5b81f39e-97e4-484b-9084-24844d9eb43b"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("7584ff8e-901b-4626-bdef-ca25f8f7261d"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("f16b135b-7ad7-4e58-9fed-b771bb71a3c7"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("467b45c6-e544-41f2-986f-853c8d6973ca"),
                    name = "",
                    description = "",
                    source = UUID.fromString("2b3f6c2c-55f8-4f04-811e-a450bdaa45c1"),
                    receiver = UUID.fromString("f16b135b-7ad7-4e58-9fed-b771bb71a3c7")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("dae0771d-4337-4f0b-8f01-2a06ac1b1ae2"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f16b135b-7ad7-4e58-9fed-b771bb71a3c7"),
                    receiver = UUID.fromString("5b81f39e-97e4-484b-9084-24844d9eb43b")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("8ec9e3df-3eb9-43f0-a5be-0bb0c5892188"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f16b135b-7ad7-4e58-9fed-b771bb71a3c7"),
                    receiver = UUID.fromString("7584ff8e-901b-4626-bdef-ca25f8f7261d")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 0), // 1
                    listOf(0, 1, 1), // 2
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2", "y3"),
                solutionX = listOf(0),
                solutionY = listOf(4, 2, 2),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network10.png">
     */
    private fun objectNetwork10(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network10",
            matI = listOf(
                listOf(1, 1),
                listOf(0, 0),
                listOf(0, 0)
            ),
            matO = listOf(
                listOf(0, 0),
                listOf(1, 0),
                listOf(0, 1)
            ),
            matC = listOf(
                listOf(-1, -1),
                listOf(1, 0),
                listOf(0, 1)
            ),
            matMarking = listOf(
                listOf(2, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("354bb1b8-c6b8-4ceb-800f-ca802ee3e6bb"),
                    name = "P1",
                    description = "",
                    tokensNumber = 2
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("764aba99-ac95-4d41-9930-9d85ffc7e966"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("285dbb58-fcb2-42e2-9353-c3815f218fff"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("94200998-afb0-4f8c-814b-f67bbad2c362"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("8bc88050-2fda-49c8-aec3-c3d4a4bdd76c"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("1627a192-9e3c-4f4e-a9e7-481d5a8c359d"),
                    name = "",
                    description = "",
                    source = UUID.fromString("354bb1b8-c6b8-4ceb-800f-ca802ee3e6bb"),
                    receiver = UUID.fromString("94200998-afb0-4f8c-814b-f67bbad2c362")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("b152ac23-3fe5-42ee-b3b4-ae6d1fe8d2e9"),
                    name = "",
                    description = "",
                    source = UUID.fromString("354bb1b8-c6b8-4ceb-800f-ca802ee3e6bb"),
                    receiver = UUID.fromString("8bc88050-2fda-49c8-aec3-c3d4a4bdd76c")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("04645e09-5232-4be7-894a-a00848514514"),
                    name = "",
                    description = "",
                    source = UUID.fromString("94200998-afb0-4f8c-814b-f67bbad2c362"),
                    receiver = UUID.fromString("764aba99-ac95-4d41-9930-9d85ffc7e966")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("58bea527-47da-42a3-b626-2c1afd624a30"),
                    name = "",
                    description = "",
                    source = UUID.fromString("8bc88050-2fda-49c8-aec3-c3d4a4bdd76c"),
                    receiver = UUID.fromString("285dbb58-fcb2-42e2-9353-c3815f218fff")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(2, 0, 0), // 1
                    listOf(1, 1, 0), // 2
                    listOf(1, 0, 1), // 3
                    listOf(0, 2, 0), // 4
                    listOf(0, 1, 1), // 5
                    listOf(0, 0, 2), // 6

                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T1", "M3"),
                    listOf("M2", "T2", "M4"),
                    listOf("M1", "T2", "M5"),
                    listOf("M5", "T1", "M4"),
                    listOf("M5", "T2", "M6"),
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2"),
                colNamesY = listOf("y1", "y2", "y3"),
                solutionX = listOf(0, 0),
                solutionY = listOf(1, 1, 1),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network11.png">
     */
    private fun objectNetwork11(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network11",
            matI = listOf(
                listOf(1),
                listOf(0)
            ),
            matO = listOf(
                listOf(0),
                listOf(1)
            ),
            matC = listOf(
                listOf(-1),
                listOf(1)
            ),
            matMarking = listOf(
                listOf(10, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("601e1d1e-cb9a-4734-ac31-70b2dea41897"),
                    name = "P1",
                    description = "",
                    tokensNumber = 10
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("490d63a4-0fce-48ff-a2a4-0ebefe5233b5"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("f448084d-b1f4-4546-af3a-707f8a77aa2d"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("aaa60ce2-9e68-403f-af1e-55256ceba67c"),
                    name = "",
                    description = "",
                    source = UUID.fromString("601e1d1e-cb9a-4734-ac31-70b2dea41897"),
                    receiver = UUID.fromString("f448084d-b1f4-4546-af3a-707f8a77aa2d")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("bfc790a3-5b44-4b5f-9893-450054bde10d"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f448084d-b1f4-4546-af3a-707f8a77aa2d"),
                    receiver = UUID.fromString("490d63a4-0fce-48ff-a2a4-0ebefe5233b5")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(10, 0), // 1
                    listOf(9, 1), // 2
                    listOf(8, 2), // 3
                    listOf(7, 3), // 4
                    listOf(6, 4), // 5
                    listOf(5, 5), // 6
                    listOf(4, 6), // 7
                    listOf(3, 7), // 8
                    listOf(2, 8), // 9
                    listOf(1, 9), // 10
                    listOf(0, 10), // 11
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T1", "M3"),
                    listOf("M3", "T1", "M4"),
                    listOf("M4", "T1", "M5"),
                    listOf("M5", "T1", "M6"),
                    listOf("M6", "T1", "M7"),
                    listOf("M7", "T1", "M8"),
                    listOf("M8", "T1", "M9"),
                    listOf("M9", "T1", "M10"),
                    listOf("M10", "T1", "M11")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2"),
                solutionX = listOf(0),
                solutionY = listOf(1, 1),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network12.png">
     */
    private fun objectNetwork12(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network12",
            matI = listOf(
                listOf(1, 0),
                listOf(0, 1)
            ),
            matO = listOf(
                listOf(0, 1),
                listOf(1, 0)
            ),
            matC = listOf(
                listOf(-1, 1),
                listOf(1, -1)
            ),
            matMarking = listOf(
                listOf(1, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("e82d37b6-0d76-4fcc-86aa-3a43773f0b3b"),
                    name = "P1",
                    description = "",
                    tokensNumber = 1
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("0fa30379-6d3e-41da-bf70-876c5077cb0b"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("6886fb6e-f54a-4242-8dd7-98ec48050d78"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("a3c65561-9013-47dd-a58b-5b945ad16284"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("d8c6afb4-0fbd-434c-b02d-1271f00996f4"),
                    name = "",
                    description = "",
                    source = UUID.fromString("e82d37b6-0d76-4fcc-86aa-3a43773f0b3b"),
                    receiver = UUID.fromString("6886fb6e-f54a-4242-8dd7-98ec48050d78")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("cf2569f1-e7b6-420a-afea-5a2f41c2ade8"),
                    name = "",
                    description = "",
                    source = UUID.fromString("6886fb6e-f54a-4242-8dd7-98ec48050d78"),
                    receiver = UUID.fromString("0fa30379-6d3e-41da-bf70-876c5077cb0b")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("51566e16-7690-4f89-8ea3-eec9c76771c4"),
                    name = "",
                    description = "",
                    source = UUID.fromString("0fa30379-6d3e-41da-bf70-876c5077cb0b"),
                    receiver = UUID.fromString("a3c65561-9013-47dd-a58b-5b945ad16284")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("7e9de6e1-4997-4a5a-a101-e5a6a01d56f0"),
                    name = "",
                    description = "",
                    source = UUID.fromString("a3c65561-9013-47dd-a58b-5b945ad16284"),
                    receiver = UUID.fromString("e82d37b6-0d76-4fcc-86aa-3a43773f0b3b")
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0), // 1
                    listOf(0, 1), // 2
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T2", "M1")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2"),
                colNamesY = listOf("y1", "y2"),
                solutionX = listOf(1, 1),
                solutionY = listOf(1, 1),
                isConsistent = true,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network13.png">
     */
    private fun objectNetwork13(): MockedPetriNet {
        return MockedPetriNet(
            networkObject = "network13",
            matI = listOf(
                listOf(1, 1, 0),
                listOf(0, 0, 0),
                listOf(0, 0, 1),
                listOf(0, 0, 1),
                listOf(0, 0, 0),
            ),
            matO = listOf(
                listOf(0, 0, 0),
                listOf(0, 0, 1),
                listOf(1, 0, 0),
                listOf(0, 1, 0),
                listOf(0, 0, 0),
            ),
            matC = listOf(
                listOf(-1, -1, 0),
                listOf(0, 0, 1),
                listOf(1, 0, -1),
                listOf(0, 1, -1),
                listOf(0, 0, 0),
            ),
            matMarking = listOf(
                listOf(2, 0, 0, 0, 0)
            ),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("22bde8c4-f80b-43eb-911f-edd2a15f81cf"),
                    name = "P1",
                    description = "",
                    tokensNumber = 2
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("f60ef032-79b0-4d14-9afc-53d97a142501"),
                    name = "P2",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("f12e9cf6-c0e9-4672-b674-8c0e9d5cf078"),
                    name = "P3",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("1d3db9f1-1bc7-4c24-9d2f-3b730aa8f067"),
                    name = "P4",
                    description = "",
                    tokensNumber = 0
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("950f8fb2-2fcb-4442-b8af-e2966044ad68"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("29e75dc2-9827-4225-8aad-9331b7d011fe"),
                    name = "T2",
                    description = ""
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("7aa81638-da83-4bb3-8689-8aaf024f2e06"),
                    name = "T3",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("6a4a66f1-c30e-452f-a083-eede1219cfe7"),
                    name = "",
                    description = "",
                    source = UUID.fromString("22bde8c4-f80b-43eb-911f-edd2a15f81cf"),
                    receiver = UUID.fromString("950f8fb2-2fcb-4442-b8af-e2966044ad68")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("0e56ad60-181c-4a96-9182-a0dc61fb7460"),
                    name = "",
                    description = "",
                    source = UUID.fromString("22bde8c4-f80b-43eb-911f-edd2a15f81cf"),
                    receiver = UUID.fromString("29e75dc2-9827-4225-8aad-9331b7d011fe")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("ee6528eb-dc8a-448d-948d-d7ea511eedf4"),
                    name = "",
                    description = "",
                    source = UUID.fromString("29e75dc2-9827-4225-8aad-9331b7d011fe"),
                    receiver = UUID.fromString("1d3db9f1-1bc7-4c24-9d2f-3b730aa8f067")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("c7be3127-7e37-4115-bf14-c855943be243"),
                    name = "",
                    description = "",
                    source = UUID.fromString("950f8fb2-2fcb-4442-b8af-e2966044ad68"),
                    receiver = UUID.fromString("f12e9cf6-c0e9-4672-b674-8c0e9d5cf078")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("8eb6a0ca-5d2e-4d5f-b490-9a0e51be5772"),
                    name = "",
                    description = "",
                    source = UUID.fromString("f12e9cf6-c0e9-4672-b674-8c0e9d5cf078"),
                    receiver = UUID.fromString("7aa81638-da83-4bb3-8689-8aaf024f2e06")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("e196d244-76f5-4fd0-955d-495494643042"),
                    name = "",
                    description = "",
                    source = UUID.fromString("1d3db9f1-1bc7-4c24-9d2f-3b730aa8f067"),
                    receiver = UUID.fromString("7aa81638-da83-4bb3-8689-8aaf024f2e06")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("25ccb969-a111-404c-85a2-b160ae445c77"),
                    name = "",
                    description = "",
                    source = UUID.fromString("7aa81638-da83-4bb3-8689-8aaf024f2e06"),
                    receiver = UUID.fromString("f60ef032-79b0-4d14-9afc-53d97a142501")
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("08c6b1e6-f14a-4180-a014-9bf5eca2ec0a"),
                    name = "P5",
                    description = "",
                    tokensNumber = 0
                )
            ),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(2, 0, 0, 0, 0), // 1
                    listOf(1, 0, 1, 0, 0), // 2
                    listOf(1, 0, 0, 1, 0), // 3
                    listOf(0, 0, 1, 1, 0), // 4
                    listOf(0, 0, 2, 0, 0), // 5
                    listOf(0, 0, 0, 2, 0), // 6
                    listOf(0, 1, 0, 0, 0), // 7
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2"),
                    listOf("M2", "T1", "M3"),
                    listOf("M2", "T2", "M4"),
                    listOf("M4", "T3", "M5"),
                    listOf("M1", "T2", "M6"),
                    listOf("M6", "T1", "M4"),
                    listOf("M6", "T2", "M7")
                )
            ),
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1", "x2", "x3"),
                colNamesY = listOf("y1", "y2", "y3", "y4", "y5"),
                solutionX = listOf(0, 0, 0),
                solutionY = listOf(2, 4, 2, 2, 2),
                isConsistent = false,
                isConservative = true
            )
        )
    }

    /**
     * <img src="img\network14.png">
     */
    private fun objectNetwork14_1(): MockedPetriNet {
        return objectNetworkOf14(
            networkObject = "network14_1",
            marking = listOf(1, 0, 0),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 0, 0),
                ),
                transitions = listOf()
            )
        )
    }

    /**
     * <img src="img\network14.png">
     */
    private fun objectNetwork14_2(): MockedPetriNet {
        return objectNetworkOf14(
            networkObject = "network14_2",
            marking = listOf(1, 1, 0),
            reachabilityTree = MockedReachabilityTreeResult(
                markings = listOf(
                    listOf(1, 1, 0),
                    listOf(0, 0, 1),
                ),
                transitions = listOf(
                    listOf("M1", "T1", "M2")
                )
            )
        )
    }

    private fun objectNetworkOf14(
        networkObject: String,
        marking: List<Int>,
        reachabilityTree: MockedReachabilityTreeResult
    ): MockedPetriNet {
        return MockedPetriNet(
            networkObject = networkObject,
            matI = listOf(
                listOf(1),
                listOf(1),
                listOf(0)
            ),
            matO = listOf(
                listOf(0),
                listOf(0),
                listOf(1)
            ),
            matC = listOf(
                listOf(-1),
                listOf(-1),
                listOf(1)
            ),
            matMarking = listOf(marking),
            objects = listOf(
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("8a4e984f-1669-404e-aca7-d2c7b96ac43c"),
                    name = "P1",
                    description = "",
                    tokensNumber = marking[0]
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("4b745864-2ccb-4c50-8b98-d4c65f37cc5f"),
                    name = "P2",
                    description = "",
                    tokensNumber = marking[1]
                ),
                WorkspaceObjectBrief.Position(
                    id = UUID.fromString("1581c89a-654a-4720-8274-d8cf7c3e9b21"),
                    name = "P3",
                    description = "",
                    tokensNumber = marking[2]
                ),
                WorkspaceObjectBrief.Transition(
                    id = UUID.fromString("2b9ebc88-3a2b-493a-b620-71078120c8e9"),
                    name = "T1",
                    description = ""
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("689d9ed5-33da-4b8b-a466-417ad6910b61"),
                    name = "",
                    description = "",
                    source = UUID.fromString("8a4e984f-1669-404e-aca7-d2c7b96ac43c"),
                    receiver = UUID.fromString("2b9ebc88-3a2b-493a-b620-71078120c8e9")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("0a949be3-7fb8-4426-b432-f608f4633c5c"),
                    name = "",
                    description = "",
                    source = UUID.fromString("4b745864-2ccb-4c50-8b98-d4c65f37cc5f"),
                    receiver = UUID.fromString("2b9ebc88-3a2b-493a-b620-71078120c8e9")
                ),
                WorkspaceObjectBrief.Arc(
                    id = UUID.fromString("95448132-d469-4406-9811-6a3c2f06dac5"),
                    name = "",
                    description = "",
                    source = UUID.fromString("2b9ebc88-3a2b-493a-b620-71078120c8e9"),
                    receiver = UUID.fromString("1581c89a-654a-4720-8274-d8cf7c3e9b21")
                )
            ),
            reachabilityTree = reachabilityTree,
            murataSolution = MockedMurataSolution(
                colNamesX = listOf("x1"),
                colNamesY = listOf("y1", "y2", "y3"),
                solutionX = listOf(0),
                solutionY = listOf(1, 1, 2),
                isConsistent = false,
                isConservative = true
            )
        )
    }
}


