package app
import grails.converters.JSON

class BarterController {

    def index = {
    }

    def create = {
        println(params)
        params.itemStartDate=new Date()
        params.itemEndDate=new Date() + Integer.parseInt(params.itemTime.getAt(0))

        if(params.itemCategory=="Vehicles") {
            params.itemCategory2 = "Automotive"
        } else if(params.itemCategory=="Automotive Parts") {
            params.itemCategory2 = "Automotive"
        } else if(params.itemCategory=="Automotive Accessories") {
            params.itemCategory2 = "Automotive"
        }

        else if(params.itemCategory=="Baby Clothes & Shoes") {
            params.itemCategory2 = "Baby Care"
        } else if(params.itemCategory=="Baby Food") {
            params.itemCategory2 = "Baby Care"
        } else if(params.itemCategory=="Other Baby Care Products") {
            params.itemCategory2 = "Baby Care"
        }

        else if(params.itemCategory=="Children's Books") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Comics") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Computer, IT , Internet") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Fiction Books") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Stationery") {
            params.itemCategory2 = "Books & Stationery"
        }

        else if(params.itemCategory=="Men's Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Women's Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Maternity Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Clothing Accessories") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Shoes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        }

        else if(params.itemCategory=="Furniture") {
            params.itemCategory2 = "Home & Garden"
        } else if(params.itemCategory=="Gardening & Plants") {
            params.itemCategory2 = "Home & Garden"
        } else if(params.itemCategory=="Other Home & Gardening Items") {
            params.itemCategory2 = "Home & Garden"
        }

        else if(params.itemCategory=="Music") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Video") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Musical Instruments") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Video Games") {
            params.itemCategory2 = "Music & Multimedia"
        }

        else if(params.itemCategory=="Bath & Body") {
            params.itemCategory2 = "Health & Beauty"
        } else if(params.itemCategory=="Beauty Tools") {
            params.itemCategory2 = "Health & Beauty"
        } else if(params.itemCategory=="Other Health & Beauty Items") {
            params.itemCategory2 = "Health & Beauty"
        }

        else if(params.itemCategory=="Sporting Goods") {
            params.itemCategory2 = "Sports"
        }

        else if(params.itemCategory=="All Other Items") {
            params.itemCategory2 = "Miscellaneous"
        }

        else if(params.itemCategory=="Television & Monitors") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Mobile Devices") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Household Appliances") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Computers") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Computer Peripherals") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Other electronic items") {
            params.itemCategory2 = "Electronics"
        }

        else if(params.itemCategory=="Plushies") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Coins") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Antiques") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Toys") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Stamps") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Art") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Other Collectable items") {
            params.itemCategory2 = "Collectables"
        }

        params.resident=Resident.findById(params.resident)

        println(params)

        def toBeSaved=new Barter(params)
        toBeSaved.save()
        println("=== ERROR ===" + toBeSaved.getErrors())

        println("zomgggggggggggggggggggggggggggggggggggg"+params)
        def barterList=Barter.findAllByResident(params.resident)
        render barterList as JSON
    }

    def uploadPhoto = {
        println(params)
        def toBeSaved=new ItemPhoto(params)
        //Retrieve the file object from the request
        def uploadedFile = request.getFile('photoName')
        toBeSaved.photoName = uploadedFile.originalFilename
        //Assign its file name to the domain object to be saved
        if( toBeSaved.save() )
        {  //Save domain object
            //Create the folder “/photo” in my web application
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir, "/images/database")
            userDir.mkdirs()
            //Save file contents into a new file in “/photo” folder.

                uploadedFile.transferTo(   new File(userDir, uploadedFile.originalFilename)   )
            println("=== ERROR ===" + toBeSaved.getErrors())
        }
        else
        {
            println("=== ERROR ===" + toBeSaved.getErrors() )
        }

        render status: 204
    }

    def searchcategory = {
        println("here")
        def catList=Category.findAllByKeyword(params.itemCategorySearch)
        render catList as JSON
    }

    def list = {
        //println(params.items.replaceAll("\\+", " "))
        def barterList=Barter.findAllByItemCategory2(params.items.replaceAll("\\+", " "))
        def residentList=[]
        for(Barter b: barterList)
        {
            residentList.add(b.resident)
        }
        def list=[barterList, residentList]
        println(barterList.size())
        render list as JSON
    }

    def listyouritems = {
        println(params)
        def res=Resident.findById(params.resident)
        println(res)
        def barterList=Barter.findAllByResident(res)
        render barterList as JSON
    }

    def createRequest = {
        println(params)

        params.requestStatus="0"
        def toBeSaved = new BarterRequest(params)
        toBeSaved.save()
        println("=== ERROR ===" + toBeSaved.getErrors() )
    }

    def listRequest = {
        println("this one"+params)
        def toBeRetrieved = BarterRequest.createCriteria().list
        {
            eq ("partytwo", params.resident)
            or {
                eq ("barterAction", "Trade with items")
                eq ("barterAction", "Give away")
            }
        }
        
        for(BarterRequest retrieved : toBeRetrieved)
        {
            retrieved.partyonename=Resident.findById(retrieved.partyone).name
            retrieved.partytwoname=Resident.findById(retrieved.partytwo).name
        }
        render toBeRetrieved as JSON
    }

    def viewitem ={
        println(params)
        def toBeRetrieved = Barter.findById(params.id)
        def list=[toBeRetrieved, toBeRetrieved.resident]
        render list as JSON
    }

    def edit = {
        println(params)
        def toBeUpdated=Barter.findById(params.id)
        params.itemStartDate=new Date()
        params.itemEndDate=new Date() + Integer.parseInt(params.itemTime.getAt(0))
        params.itemValue=params.itemValue.toDouble()

        if(params.itemCategory=="Vehicles") {
            params.itemCategory2 = "Automotive"
        } else if(params.itemCategory=="Automotive Parts") {
            params.itemCategory2 = "Automotive"
        } else if(params.itemCategory=="Automotive Accessories") {
            params.itemCategory2 = "Automotive"
        }

        else if(params.itemCategory=="Baby Clothes & Shoes") {
            params.itemCategory2 = "Baby Care"
        } else if(params.itemCategory=="Baby Food") {
            params.itemCategory2 = "Baby Care"
        } else if(params.itemCategory=="Other Baby Care Products") {
            params.itemCategory2 = "Baby Care"
        }

        else if(params.itemCategory=="Children's Books") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Comics") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Computer, IT , Internet") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Fiction Books") {
            params.itemCategory2 = "Books & Stationery"
        } else if(params.itemCategory=="Stationery") {
            params.itemCategory2 = "Books & Stationery"
        }

        else if(params.itemCategory=="Men's Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Women's Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Maternity Clothes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Clothing Accessories") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        } else if(params.itemCategory=="Shoes") {
            params.itemCategory2 = "Clothing, Shoes & Accessories"
        }

        else if(params.itemCategory=="Men's Clothes") {
            params.itemCategory2 = "Furniture"
        } else if(params.itemCategory=="Women's Clothes") {
            params.itemCategory2 = "Gardening & Plants"
        } else if(params.itemCategory=="Maternity Clothes") {
            params.itemCategory2 = "Other Home & Gardening Items"
        }

        else if(params.itemCategory=="Music") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Video") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Musical Instruments") {
            params.itemCategory2 = "Music & Multimedia"
        } else if(params.itemCategory=="Video Games") {
            params.itemCategory2 = "Music & Multimedia"
        }

        else if(params.itemCategory=="Bath & Body") {
            params.itemCategory2 = "Health & Beauty"
        } else if(params.itemCategory=="Beauty Tools") {
            params.itemCategory2 = "Health & Beauty"
        } else if(params.itemCategory=="Other Health & Beauty Items") {
            params.itemCategory2 = "Health & Beauty"
        }

        else if(params.itemCategory=="Sporting Goods") {
            params.itemCategory2 = "Sports"
        }

        else if(params.itemCategory=="All") {
            params.itemCategory2 = "Miscellaneous"
        }

        else if(params.itemCategory=="Television & Monitors") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Mobile Devices") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Household Appliances") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Computer Peripherals") {
            params.itemCategory2 = "Electronics"
        } else if(params.itemCategory=="Other electronic items") {
            params.itemCategory2 = "Electronics"
        }

        else if(params.itemCategory=="Plushies") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Coins") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Antiques") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Toys") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Stamps") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Art") {
            params.itemCategory2 = "Collectables"
        } else if(params.itemCategory=="Other Collectable items") {
            params.itemCategory2 = "Collectables"
        }

        toBeUpdated.itemName=params.itemName
        toBeUpdated.itemPhoto=params.itemPhoto
        toBeUpdated.itemCondition=params.itemCondition
        toBeUpdated.itemValue=params.itemValue
        toBeUpdated.itemCategory=params.itemCategory
        toBeUpdated.itemCategory2=params.itemCategory2
        toBeUpdated.itemDescription=params.itemDescription
        toBeUpdated.itemStartAction=params.itemStartAction
        toBeUpdated.itemEndAction=params.itemEndAction
        toBeUpdated.itemStartDate=params.itemStartDate
        toBeUpdated.itemEndDate=params.itemEndDate


        render toBeUpdated as JSON
    }

    def ragandboneman = {
        
    }

}