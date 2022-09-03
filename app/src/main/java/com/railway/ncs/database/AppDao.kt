package com.kpk.eChallan.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpk.eChallan.database_tables.CitiesTable
import com.kpk.eChallan.database_tables.TicketsTable
import com.railway.ncs.database_tables.AllTrainsTable
import com.railway.ncs.model.AllTrainsData


@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCities(citiesTable : CitiesTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrainsData(allTrainsTable: AllTrainsTable)


    @Insert(onConflict = OnConflictStrategy.FAIL)
    suspend fun addTicket(ticketsTable: TicketsTable)

    @Query("SELECT * FROM TicketsTable")
    fun getBookedTicket(): LiveData<List<TicketsTable>>


    @Query("SELECT ticketId, COUNT(*) AS count FROM TicketsTable")
    fun getTicketCount():Int

    @Query("SELECT * FROM CitiesTable")
    fun getCities(): LiveData<List<CitiesTable>>

    @Query("SELECT * FROM AllTrainsTable")
    fun getAllTrains(): LiveData<List<AllTrainsTable>>



/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend  fun insertCitizen(citizens: TableAddCitizens)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun InsertBatchList(tableBatchList: TableBatchList)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend   fun insertZoneList(tableZone: TableZone)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend  fun insertViolationList(violationList: TableViolationList)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPoliceStationList(stationsList: TablePoliceStationsList)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVehicleTypeList(tableVehicleTypeList: TableVehicleTypeList)

    @Query("SELECT * FROM user")
     fun getUser(): LiveData<List<User>>

    @Query("SELECT * FROM TableUtil")
    fun getUtilConstants(): LiveData<List<TableUtil>>

    @Query("SELECT * FROM TableAddCitizens")
    fun getAllCitizen(): LiveData<List<TableAddCitizens>>

    @Query("SELECT *  FROM TableVehicleTypeList ")
    fun getAllVehicleTypeList():LiveData<List<TableVehicleTypeList>>

    @Query("SELECT *  FROM TablePoliceStationsList ")
    fun getAllPoliceStationList():LiveData<List<TablePoliceStationsList>>

    @Query("SELECT *  FROM TableZone ")
    fun getAllZoneList():LiveData<List<TableZone>>

    @Query("SELECT *  FROM TableBatchList ")
    fun getAllBatchList(): LiveData<List<TableBatchList>>

   // @Query("Select COL_TO_BRANCH_E_TICKET_ID , COL_CHALLAN_DETAIL ,  COL_Challan_Sync , COL_isImpound ,  COL_HOB ,  COL_USER_ID ,  COL_VIOLATOR_NAME , COL_VIOLATOR_CNIC ,  COL_VIOLATION_AMOUNT ,  COL_DATE ,  COL_HOV ,  COL_HOV_Date , COL_Hov_Sync ,  COL_P_Staus ,  COL_DATE ,  COL_Paid_sync ,  COL_Unique_ID , COL_JSTicket ,  COL_Warning ,  COL_Sync ,  COL_PrintReceipt ,  COL_PrintPaymentReceipt  FROM  TablePaymentList  WHERE  COL_TO_BRANCH_E_TICKET_ID = :id  OR  COL_TO_BRANCH_E_TICKET_ID  = :id2 ")
   @Query("SELECT * FROM tablepaymentlist WHERE COL_TO_BRANCH_E_TICKET_ID = :id  OR  COL_TO_BRANCH_E_TICKET_ID  = :id2 ")
   fun getChallansForSyncInsertion(id:String,id2:String): LiveData<List<TablePaymentList>>
   // @Query("Select COL_TO_BRANCH_E_TICKET_ID , COL_CHALLAN_DETAIL , COL_Challan_Sync , COL_isImpound , COL_HOB , COL_USER_ID , COL_VIOLATOR_NAME , COL_VIOLATOR_CNIC , COL_VIOLATION_AMOUNT , COL_DATE , COL_HOV , COL_HOV_Date , COL_Hov_Sync , COL_P_Staus , COL_DATE , COL_Paid_sync , COL_Unique_ID , COL_JSTicket , COL_Warning , COL_Sync , COL_PrintReceipt , COL_PrintPaymentReceipt  From TablePaymentList  WHERE COL_Paid_sync = 0  AND COL_P_Staus  =  :paid_status ")
    @Query("SELECT * FROM tablepaymentlist WHERE COL_Paid_sync = :id  AND COL_P_Staus  =  :paid_status ")
    fun getChallansForSyncUpdateDayend(id: String,paid_status:String): LiveData<List<TablePaymentList>>



    @Query("SELECT * FROM tableviolationlist WHERE COL_V_Class = :name ")
    fun getViolation(name:String): LiveData<List<TableViolationList>>






    @Query("UPDATE user SET status = :status  WHERE name = :user")
    suspend fun updateLogedStatus(status:String,user:String)

    @Query("DELETE FROM user")
     suspend fun deleteUser()

     @Query( "DELETE FROM TableBatchList")
     suspend fun deleteBatchList()

   @Query("DELETE FROM TableZone")
    suspend fun deleteZones()

    @Query("DELETE FROM TableChallans")
   suspend  fun deleteChallans()

    @Query("DELETE FROM TableChallansToBranch")
     fun deleteChallansToBranch()

    @Query("DELETE FROM TableAddCitizens")
    suspend fun deleteCitizens()

    @Query("DELETE FROM TableVehicleTypeList")
    suspend fun deleteVehicles()

    @Query("DELETE FROM TableViolationList")
  suspend   fun deleteViolations()

    @Query("DELETE FROM TablePoliceStationsList")
   suspend fun deletePolicestations()

 @Query("SELECT Col_Citizens_Id, COUNT(*) AS userCount FROM TableAddCitizens ")
 fun getAllCitizenListCount():Int









 @Query("SELECT COL_VIOLATION_ID, COUNT(*) AS violationCount FROM TableViolationList ")
 fun getAllViolationsListCount():Int

 @Query("SELECT COL_Police_Station_Auto_Id, COUNT(*) AS violationCount FROM TablePoliceStationsList ")
 fun getAllPoliceStationListCount(): Int




 @Insert(onConflict = OnConflictStrategy.IGNORE)
 fun InsertViolationList(tableViolationList: TableViolationList)*/


}
