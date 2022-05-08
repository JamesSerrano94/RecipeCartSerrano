package com.example.recipecartnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SearchFragment extends Fragment
{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 6;

    //FeedReaderDbHelper helper;
    DatabaseHelper myDB;
    List<recipeDescription> recipes = new ArrayList<>();

    RecyclerView recyclerView;
    private SearchRecyclerViewAdapter.OnNoteListener monNoteListener;
    private SearchRecyclerViewAdapter mAdapter;






    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {

    }

    public void acceptsInterfaceObject(SearchRecyclerViewAdapter.OnNoteListener obj){
        monNoteListener = obj;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchFragment newInstance(int columnCount) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //helper= new FeedReaderDbHelper(this.getContext());
        myDB = new DatabaseHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearch);

        //recipes = helper.loadHandler();
        if (myDB.getAllGlobalRecipes().size() == 0) {
            myDB.insertDataGlobalRecipe("Miso-Butter Roast Chicken",
                    "Chicken 1.0 None, Salt .05 Gallon, Squash 2.0 None, Sage .04 Gallon, Rosemary .03 Gallon, Butter .07 Gallon, Allspice .02 Gallon, Red Pepper 1.0 None, Black Pepper 1.0 None, Bread 1.0 None, Apple 2.0 None, Olive Oil .02 Gallon, Onion 1.0 None, Vinegar .03 Gallon, Miso .02 Gallon, Flour .02 Gallon",
                    "Pat chicken dry with paper towels, season all over with 2 tsp. salt, and tie legs together with kitchen twine. Let sit at room temperature 1 hour.\n" +
                            "Meanwhile, halve squash and scoop out seeds. Run a vegetable peeler along ridges of squash halves to remove skin. Cut each half into ½\"-thick wedges; arrange on a rimmed baking sheet.\n" +
                            "Combine sage, rosemary, and 6 Tbsp. melted butter in a large bowl; pour half of mixture over squash on baking sheet. Sprinkle squash with allspice, red pepper flakes, and ½ tsp. salt and season with black pepper; toss to coat.\n" +
                            "Add bread, apples, oil, and ¼ tsp. salt to remaining herb butter in bowl; season with black pepper and toss to combine. Set aside.\n" +
                            "Place onion and vinegar in a small bowl; season with salt and toss to coat. Let sit, tossing occasionally, until ready to serve.\n" +
                            "Place a rack in middle and lower third of oven; preheat to 425°F. Mix miso and 3 Tbsp. room-temperature butter in a small bowl until smooth. Pat chicken dry with paper towels, then rub or brush all over with miso butter. Place chicken in a large cast-iron skillet and roast on middle rack until an instant-read thermometer inserted into the thickest part of breast registers 155°F, 50–60 minutes. (Temperature will climb to 165°F while chicken rests.) Let chicken rest in skillet at least 5 minutes, then transfer to a plate; reserve skillet.\n" +
                            "Meanwhile, roast squash on lower rack until mostly tender, about 25 minutes. Remove from oven and scatter reserved bread mixture over, spreading into as even a layer as you can manage. Return to oven and roast until bread is golden brown and crisp and apples are tender, about 15 minutes. Remove from oven, drain pickled onions, and toss to combine. Transfer to a serving dish.\n" +
                            "Using your fingers, mash flour and butter in a small bowl to combine.\n" +
                            "Set reserved skillet with chicken drippings over medium heat. You should have about ¼ cup, but a little over or under is all good. (If you have significantly more, drain off and set excess aside.) Add wine and cook, stirring often and scraping up any browned bits with a wooden spoon, until bits are loosened and wine is reduced by about half (you should be able to smell the wine), about 2 minutes. Add butter mixture; cook, stirring often, until a smooth paste forms, about 2 minutes. Add broth and any reserved drippings and cook, stirring constantly, until combined and thickened, 6–8 minutes. Remove from heat and stir in miso. Taste and season with salt and black pepper.\n" +
                            "Serve chicken with gravy and squash panzanella alongside.", R.drawable.miso_butter_roast_chicken_acorn_squash_panzanella);

            myDB.insertDataGlobalRecipe("Crispy Salt and Pepper Potatoes",
                    "egg whites 2.0 None, potatoes 1.0 Lbs, kosher salt 0.2 Lbs, ground black pepper 0.3 Lbs, finely chopped rosemary 0.1 Lbs, finely chopped thyme 0.1 Lbs, finely chopped parsley 0.1 Lbs",
                    "Preheat oven to 400°F and line a rimmed baking sheet with parchment. In a large bowl, whisk the egg whites until foamy (there shouldn’t be any liquid whites in the bowl)." +
                            " Add the potatoes and toss until they’re well coated with the egg whites, then transfer to a strainer or colander and let the excess whites drain. Season the potatoes with the salt, pepper, and herbs." +
                            " Scatter the potatoes on the baking sheet (make sure they’re not touching) and roast until the potatoes are very crispy and tender when poked with a knife, 15 to 20 minutes (depending on the size of the potatoes) Transfer to a bowl and serve.",
                    R.drawable.crispy_salt_and_pepper_potatoes_dan_kluger);
            myDB.insertDataGlobalRecipe("Thanksgiving Mac and Cheese",
                    "evaporated milk 1 None, whole milk 1 Gallon,  garlic powder 0.1 Lbs, onion powder 0.1 Lbs,  smoked paprika 0.1 Lbs, freshly ground black pepper 0.5 Lbs, kosher salt 0.1 Lbs, extra-sharp cheddar 2 Lbs, full-fat cream cheese 4 Lbs, elbow macaroni 1 None",
                    "Place a rack in middle of oven; preheat to 400°. Bring evaporated milk and whole milk to a bare simmer in a large saucepan over medium heat. Whisk in garlic powder, onion powder, paprika, pepper, and 1 tsp. salt. "
                            + "Working in batches, whisk in three fourths of the cheddar, then all of the cream cheese. Meanwhile, bring a large pot of generously salted water to a boil (it should have a little less salt than seawater). Cook macaroni, stirring occasionally, until very al dente, about 4 minutes. Drain in a colander."
                            + "Add macaroni to cheese sauce in pan and mix until well coated. Evenly spread out half of macaroni mixture in a 13x9 baking dish. Sprinkle half of remaining cheddar evenly over"
                            + "Layer remaining macaroni mixture on top and sprinkle with remaining cheddar. Bake until all of the cheese is melted, about 10 minutes. Let cool slightly before serving.", R.drawable.thanksgivingmacandcheese);
            myDB.insertDataGlobalRecipe("Italian Sausage and Bread Stuffing",
                    "round Italian loaf 1 None, olive oil .05 Gallon,sweet Italian sausage 2 None, stick unsalted butter 0.5 Lbs, medium onions 3 None, large celery ribs 4 None, garlic cloves 5 None, large eggs 4 None, heavy cream 0.4 None, turkey giblet stock 0.5 Gallon, grated Parmigiano-Reggiano 1 None, coarsely chopped flat-leaf parsley 0.05 Lbs, shallow ceramic 4 None",
                    "Preheat oven to 350°F with rack in middle. Generously butter baking dish.\n" +
                            "        Put bread in 2 shallow baking pans and bake, switching position of pans halfway through baking, until just dried out, about 10 minutes.\n" +
                            "                Heat 1 tablespoon oil in a 12-inch heavy skillet over medium-high heat until it shimmers, then cook half of sausage, stirring and breaking it into small pieces, until golden brown, about 6 minutes. Transfer with a slotted spoon to a large bowl. Brown remaining sausage in remaining tablespoon oil, transferring to bowl.\n" +
                            "        Pour off fat from skillet and wipe clean. Heat butter over medium heat until foam subsides, then cook onions, celery, garlic, and ½ teaspoon each of salt and pepper, stirring occasionally, until golden, 12 to 15 minutes. Add vegetables and bread to sausage.\n" +
                            "        Whisk together eggs, ½ cup cream, turkey stock, cheese, and parsley, then stir into stuffing and cool completely, about 30 minutes. Reserve 5 cups stuffing to stuff turkey and spoon remainder into baking dish, then drizzle with remaining ¼ cup cream. Cover stuffing and chill.\n" +
                            "        About 1 hour before stuffed turkey is finished roasting, bring dish of stuffing to room temperature. When turkey is done, increase oven temperature to 425°F and bake stuffing, covered tightly with foil, until hot throughout, about 20 minutes. Remove foil and bake until top is golden and crisp, about 15 minutes more.\n" +
                            "        Cooks'' Note: Bread can be toasted 3 days ahead and kept (once cool) in a sealed bag at room temperature. Stuffing can be prepared (but not baked) 4 hours before roasting turkey. If baking stuffing at the same time as potatoes, put stuffing in upper third of oven and potatoes in bottom third (allow extra time).", R.drawable.italiansausageandbreadstuffing240559);

            myDB.insertDataGlobalRecipe("Newton''s Law",
                    "dark brown sugar 0.1 Lbs, hot water 0.1 Gallon, bourbon .05 Gallon, fresh lemon juice 1.5 None, apple butter 0.2 Lbs, ground cinnamon 0.5 None",
                    "Stir together brown sugar and hot water in a cocktail shaker to dissolve. Let cool, then add bourbon, lemon juice, and apple butter and fill with ice. Shake until well chilled, about 15 seconds. Strain into an ice-filled rocks glass. Garnish with orange twist and cinnamon.",
                    R.drawable.newtonslawapplebourboncocktail);
            myDB.insertDataGlobalRecipe("Instant Pot Lamb Haleem",
                    "white jasmine rice 0.75 Lbs, pearl barley 0.25 Lbs, bone-in lamb stew meat 1.5 Lbs, kosher salt 0.3 Lbs, piece fresh ginger 6 None, shallots 3 None, vegetable oil 0.3 Lbs, garlic cloves 4 None, garam masala 1.5 None, cayenne pepper 0.1 Lbs, ground turmeric 0.1 Lbs, green Thai chiles 3 None, cilantro 0.5 None, white onion 0.5 None, limes 2 None",
                    "Combine dals, rice, and barley in a medium bowl. Pour in water to cover and swish dals and grains around with your fingers. Drain and repeat process until water runs clear enough to see your hands through. Pour in water to cover and let dals and grains soak at room temperature at least 2 hours, or cover and chill up to 12 hours.\n" +
                            "        Place lamb on a plate, pat dry with paper towels, and season all over with 2 tsp. salt; set aside. Peel ginger by scraping off skin with a spoon. Finely grate half of ginger; set aside. Thinly slice remaining ginger. Stack slices and cut crosswise into matchsticks; set aside.\n" +
                            "        If using an electric pressure cooker, combine shallots and ghee in pot and set to sauté function or high. Cook, stirring often and adjusting heat as needed if your cooker has that option, until shallots are just beginning to brown, about 8 minutes (or about 6 minutes if cooking in oil). Reduce to normal function or medium if possible, or, if your cooker doesn’t have a lower setting, turn off for a minute if mixture is getting too hot. Add reserved ginger matchsticks and cook, stirring often, until shallots are brown and crisp and ginger is frizzled, 4–10 minutes, depending on your cooker. Using a slotted spoon, transfer shallots and ginger to a plate, spreading out into a single layer. Season lightly with salt; let cool.\n" +
                            "        Add garlic, garam masala, cayenne, turmeric, and reserved grated ginger, and cook, stirring constantly, until fragrant, about 30 seconds. Add lamb and stir to coat in spices. Cook, stirring often with a wooden spoon and repeatedly adding splashes of water as spices begin to stick to bottom of pot and scraping up browned bits, until flavors come together, about 15 minutes.(Bhuna, the process of repeated sticking and scraping, is a Southeast Asian cooking technique that brings out the flavor of the spices and ensures they don’t taste raw and grainy.)\n" +
                            "        Drain dal mixture and add to pot along with 1 tsp. salt and 5 cups water. Secure lid and bring to full pressure according to manufacturer’s directions. Cook 1½ hours. Let sit 20 minutes, then manually release pressure and open up.\n" +
                            "        Taste haleem and add more salt and cayenne if needed. Add chiles if using. (These are for flavor, not heat; feel free to leave them out.) Simmer on normal function or medium, stirring often, 5 minutes. Stir in ½ cup cilantro.\n" +
                            "        Serve haleem with white onion, lime wedges, reserved crispy shallots and ginger, and more chopped cilantro.\n" +
                            "                If using a medium Dutch oven or stovetop pressure cooker, cook shallots and ghee over high heat, stirring often, until shallots are just beginning to brown, about 8 minutes (or about 6 minutes if cooking in oil). Add reserved ginger matchsticks and cook, stirring often, until shallots are brown and crisp and ginger is frizzled, 5–8 minutes. Using a slotted spoon, transfer shallots and ginger to a plate, spreading out in a single layer. Season lightly with salt; let cool.\n" +
                            "        Reduce heat to medium, add garlic, garam masala, cayenne, turmeric, and reserved grated ginger, and cook, stirring constantly, until fragrant, about 30 seconds. Add lamb and stir to coat in spices. Cook, stirring often with a wooden spoon and repeatedly adding splashes of water as spices begin to stick to bottom of pot and scraping up browned bits, until flavors come together, about 15 minutes. (Bhuna, the process of repeated sticking and scraping, is a Southeast Asian cooking technique that brings out the flavor of the spices and ensures they don’t taste raw and grainy.)\n" +
                            "        Drain dal mixture and add to pot along with 1 tsp. salt and 6½ cups water (if using a Dutch oven) or 5 cups water (for pressure cooker). Increase heat to high and bring to a boil, then reduce heat to the low, cover pot, and cook, stirring and scraping bottom of pot every 15–20 minutes, until meat is falling off the bone and dals and grains have nearly melted into the stew, 4½–5 hours. (If using a stovetop cooker, increase heat to high and bring to a boil. Secure lid and bring to full pressure according to manufacturer’s directions. Cook 1½ hours. Let sit 20 minutes, then manually release pressure and open up.)\n" +
                            "        Taste haleem and add more salt and cayenne if needed. Add chiles if using. (These are for flavor, not heat; feel free to leave them out.) Simmer over medium heat, stirring often, 5 minutes. Stir in ½ cup cilantro.\n" +
                            "        Serve haleem with white onion, lime wedges, reserved crispy shallots and ginger, and more chopped cilantro.\n" +
                            "                Do ahead: Haleem can be made 1 day ahead. Let cool; cover and chill. Reheat over medium until warmed through.", R.drawable.instantpotlambhaleem);
            myDB.insertDataGlobalRecipe("Spiced Lentil and Caramelized Onion Baked Eggs",
                    "onion 1 None, turmeric 0.5 None, cumin 0.1 None, red pepper flakes 0.8 None, tomato paste 0.2 Lbs, eggs 3 None, olive oil 0.2 Lbs, whole cumin seeds 0.5 None, Kosher salt 0.1 None, freshly ground black pepper 0.1 None, Parsley 0.1 None"
                    , "Place an oven rack in the center of the oven, then preheat to 350°F.\n" +
                            "        In a medium, oven-safe pan, heat 1 Tbsp. olive oil over medium heat. Add 1 large, thinly sliced onion and ½ tsp. Kosher salt. Cook, stirring often, until golden brown, about 25 minutes.\n" +
                            "                Add ½ tsp. turmeric, 1 tsp. cumin, ¼ tsp. Aleppo pepper (or ⅛ tsp. crushed red pepper flakes), and 2 Tbsp. tomato paste. Cook and stir constantly until the onions are coated and the tomato paste has darkened slightly, about 2 minutes. Add ⅓ cup water; stir and scrape up all the browned bits on the bottom of the pan for 1 to 2 minutes, or until the liquid looks thickened and saucy. Add one 14-oz. can of lentil soup; cook, stirring to combine, 1 to 2 minutes. Turn off the heat and season with salt, pepper, and more Aleppo pepper or red pepper flakes to taste.\n" +
                            "                Using a spoon, create 3 wells in the lentil mixture. Carefully crack 1 egg into each well. Transfer the pan to the oven and bake until the whites of the eggs are just set, 11 to 13 minutes.\n" +
                            "                While the eggs bake, in a small pot or butter warmer, heat 2 Tbsp. unsalted butter, ghee, or olive oil over medium heat. Add ½ tsp. cumin seeds; swirl the pan until the seeds start to sizzle and brown, 30 seconds to 1 minute. Remove from the heat.\n" +
                            "        Drizzle the finished eggs with the butter mixture, season with salt and pepper, and garnish with parsley before serving.",
                    R.drawable.spicedlentilandcaramelizedonionbakedeggs);
        }

        recipes = myDB.getAllGlobalRecipes();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (monNoteListener == null) {
            acceptsInterfaceObject(new SearchRecyclerViewAdapter.OnNoteListener(){



                @Override
                public void onNoteClick(int position) {
                    //recipes.get(position);

                    getParentFragmentManager().beginTransaction().replace(getId(),
                            new RecipeViewFragment(recipes.get(position))).commit();
                    return;

                }
            });
        }
        recyclerView.setAdapter(new SearchRecyclerViewAdapter(recipes, monNoteListener));

        return view;
    }






//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.PantryButton:
//                getParentFragmentManager().beginTransaction().replace(this.getId(),
//                        new AddPantryFragment()).commit();
//                return;
//        }
//    }
}